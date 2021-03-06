package com.epam.kie.server.drools;

import com.google.gson.Gson;
import org.drools.compiler.kie.builder.impl.KieServicesImpl;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.server.api.marshalling.Marshaller;
import org.kie.server.api.marshalling.MarshallerFactory;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.impl.KieServicesClientImpl;
import org.kie.server.client.impl.RuleServicesClientImpl;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DroolsServicesImpl {
    //private static final String URL = "http://ecsa00400807.epam.com:8180/kie-server/services/rest/server"; //remote
    private static final String URL = "http://epbyminw7901.minsk.epam.com:8180/kie-server/services/rest/server"; //local
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";
    private static final String OBJECT_FORMAT_CT = "LinkedHashMap";
    private static final Long TIMEOUTS = 30000l;
    private Marshaller marshaller;
    private KieCommands kieCommands;
    private KieServicesConfiguration kieServicesConfiguration;
    private KieServicesClientImpl kieServicesClient;
    private RuleServicesClientImpl ruleServicesClient;
    private Gson gson;
    private String idLastDeployedContainer;


    //send request to the kie server
    public <T> List<T> executeCommands(List<T> listRequestObjects) {
        List responseListFromKIEServer;
        String jsonContent;

        jsonContent = createJsonRequest(listRequestObjects, getSetOfClassesFromListOfObjects(listRequestObjects));
        ServiceResponse<ExecutionResults> executeResponse = ruleServicesClient.executeCommandsWithResults(idLastDeployedContainer, jsonContent);
        responseListFromKIEServer = (List) executeResponse.getResult().getValue(String.valueOf(listRequestObjects.hashCode()));

        return convertToListOfObjects(responseListFromKIEServer);
    }

    //initialization kie server
    public void initialize() {
        kieServicesConfiguration = KieServicesFactory.newRestConfiguration(URL, USER, PASSWORD);
        kieServicesConfiguration.setTimeout(TIMEOUTS);
        kieServicesConfiguration.setMarshallingFormat(MarshallingFormat.JSON);
        kieCommands = new KieServicesImpl().getCommands();
        kieServicesClient = new KieServicesClientImpl(kieServicesConfiguration);
        idLastDeployedContainer = getLastContainerID();
        ruleServicesClient = new RuleServicesClientImpl(kieServicesConfiguration);
        ruleServicesClient.setOwner(kieServicesClient);
        gson = new Gson();
    }

    //create POST request
    private String createJsonRequest(List listRequestObjects, Set<Class<?>> externalClasses) {
        marshaller = MarshallerFactory.getMarshaller(externalClasses, MarshallingFormat.JSON, this.getClass().getClassLoader());

        List<Command<?>> commands = new ArrayList<Command<?>>();

        commands.add(kieCommands.newInsertElements(listRequestObjects, String.valueOf(listRequestObjects.hashCode())));
        commands.add(kieCommands.newFireAllRules());
        Command<?> batchCommand = kieCommands.newBatchExecution(commands);

        return marshaller.marshall(batchCommand);
    }

    //create set of unique classes for initialization them on KIE server
    private Set<Class<?>> getSetOfClassesFromListOfObjects(List listRequestObjects) {
        return (Set<Class<?>>) listRequestObjects
                .stream()
                .filter(distinctByKey(obj -> obj.getClass()))
                .map(Object::getClass)
                .collect(Collectors.toSet());
    }

    //on CT we get response in format 'LinkedHaspMap with String key and LinkedHaspMap as value'
    //it's method for converting object from Map to necessary Class
    private Object convertFromMapToObject(Object map) {
        LinkedHashMap objectMapFormat = (LinkedHashMap) ((LinkedHashMap) map).values().iterator().next();
        String className = (String) ((LinkedHashMap) map).keySet().iterator().next();
        String jsonObjectSerialized = gson.toJson(objectMapFormat);
        Object formattedObject = null;
        try {
            formattedObject = gson.fromJson(jsonObjectSerialized, Class.forName(className));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return formattedObject;
    }

    //on CT we get response in format 'LinkedHaspMap with String key and LinkedHaspMap as value'
    private List convertToListOfObjects(List responseList) {
        List listOfFormattedObjects = new ArrayList();

        responseList.stream().forEach(k -> {
            if (k.getClass().getName().contains(OBJECT_FORMAT_CT)) {
                listOfFormattedObjects.add(convertFromMapToObject(k));
            } else listOfFormattedObjects.add(k);
        });
        return listOfFormattedObjects;
    }

    //on KIE server we can get many containers, and to execute our rules we get the last deployed container
    private String getLastContainerID() {
        return kieServicesClient
                .listContainers()
                .getResult()
                .getContainers()
                .stream()
                .max(Comparator.comparing(k -> k.getMessages().stream().findFirst().get().getTimestamp()))
                .get()
                .getContainerId();
    }

    //it's used to get unique Classes
    private <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
