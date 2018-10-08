import com.google.gson.Gson;
import org.drools.compiler.kie.builder.impl.KieServicesImpl;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.server.api.marshalling.Marshaller;
import org.kie.server.api.marshalling.MarshallerFactory;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.KieContainerResource;
import org.kie.server.api.model.Message;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.*;
import org.kie.server.client.impl.KieServicesClientImpl;
import org.kie.server.client.impl.RuleServicesClientImpl;

import java.util.*;

public class DecisionServerTest {
    //private static final String URL = "http://ecsa00400807.epam.com:8180/kie-server/services/rest/server"; //remote
    private static final String URL = "http://epbyminw7901.minsk.epam.com:8180/kie-server/services/rest/server"; //local

    private static final String USER = "admin";
    private static final String PASSWORD = "admin";
    private static final String OBJECT_FORMAT_CT = "LinkedHashMap";
    private Marshaller marshaller;
    private KieCommands commandsFactory;
    private KieServicesConfiguration configuration;
    private KieServicesClientImpl clientImpl;
    private RuleServicesClientImpl rulesCl;
    private Gson gson;
    private String idContainer;

    //initialization kie server
    public void initialize(Set<Class<?>> externalClasses) {
        marshaller = MarshallerFactory.getMarshaller(MarshallingFormat.JSON, this.getClass().getClassLoader());
        configuration = KieServicesFactory.newRestConfiguration(URL, USER, PASSWORD);
        configuration.setMarshallingFormat(MarshallingFormat.JSON);
        commandsFactory = new KieServicesImpl().getCommands();

        clientImpl = new KieServicesClientImpl(configuration);
        idContainer = getLastContainerID();
        rulesCl = new RuleServicesClientImpl(configuration);
        rulesCl.setOwner(clientImpl);
        gson = new Gson();
    }

    //send request to the kie server
    public Object executeCommands(Object requestObject) {
        Object responseObject;

        String jsonContent = createJsonRequest(requestObject);
        ServiceResponse<ExecutionResults> executeResponse = rulesCl.executeCommandsWithResults(idContainer, jsonContent);
        responseObject = executeResponse.getResult().getValue(String.valueOf(requestObject.hashCode()));

        return convertToObject(responseObject, requestObject);
    }

    private String createJsonRequest(Object object) {
        List<Command<?>> commands = new ArrayList<Command<?>>();

        commands.add(commandsFactory.newInsert(object, String.valueOf(object.hashCode())));
        commands.add(commandsFactory.newFireAllRules());
        Command<?> batchCommand = commandsFactory.newBatchExecution(commands);

        return marshaller.marshall(batchCommand);
    }

    private Object convertFromMapToObject(LinkedHashMap map, Object convertToClass) {
        String jsonObjectSerialized = gson.toJson(map);
        return gson.fromJson(jsonObjectSerialized, convertToClass.getClass());
    }

    private Object convertToObject(Object responseObject, Object objectFormat) {
        LinkedHashMap objectMapFormat;
        if (responseObject.getClass().getName().contains(OBJECT_FORMAT_CT)) {
            objectMapFormat = (LinkedHashMap) ((LinkedHashMap) responseObject).values().iterator().next();
            return convertFromMapToObject(objectMapFormat, objectFormat);
        } else return responseObject;
    }

    private String getLastContainerID() {
        Date lastContainerDate = null;
        String lastContainerID = null;
        Iterator<KieContainerResource> iter;

        iter = clientImpl.listContainers().getResult().getContainers().iterator();

        while (iter.hasNext()) {
            KieContainerResource containerResource = iter.next();
            List<Message> messageList = containerResource.getMessages();
            Date dateInCurrentContainer = messageList.iterator().next().getTimestamp();
            if (lastContainerDate == null) {
                lastContainerDate = dateInCurrentContainer;
                lastContainerID = containerResource.getContainerId();
            } else {
                System.out.println(lastContainerDate + "!!!!!!!" + dateInCurrentContainer);
                if (lastContainerDate.compareTo(dateInCurrentContainer) == -1) {
                    lastContainerDate = dateInCurrentContainer;
                    lastContainerID = containerResource.getContainerId();
                }
            }
        }
        return lastContainerID;
    }

}