

import org.drools.compiler.kproject.ReleaseIdImpl;
import org.drools.core.RuleBaseConfiguration;
import org.drools.core.common.RuleBasePartitionId;
import org.drools.core.io.impl.UrlResource;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.definition.rule.Rule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;


import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class DroolsTest {




   /* public void initialisationKnowledgeBase() throws ClassNotFoundException {

        //Class clazz = Class.forName("org.drools.compiler.kie.builder.impl.KieServicesImpl").getClass();
        //System.out.println(clazz.getMethods().length);
        KieServices ks = KieServices.Factory.get();
        KieRepository kr = ks.getRepository();
        System.out.println(kr.getDefaultReleaseId());
        KieContainer kieContainer = ks.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("ksession-rules");
        kieSession.insert(product);
        kieSession.fireAllRules();

    }*/


    private void runRule(KieContainer kieKontainer) {

        KieSession newKieSession = kieKontainer.newKieSession();
        //FactHandle fact = newKieSession.insert(product);
        int result = newKieSession.fireAllRules();
        newKieSession.dispose();
    }
    public void executorExternal(Object object) throws IOException, ClassNotFoundException {
        String url = "http://localhost:8080/drools-wb/maven2/com/epam/drools/1.0.3/drools-1.0.3.jar";
        //String url = "file:///d:/drools-1.0.1.jar";

        ReleaseIdImpl releaseId = new ReleaseIdImpl("com.epam", "drools", "1.0.3");
        KieServices ks = KieServices.Factory.get();

        KieRepository kr = ks.getRepository();
        UrlResource urlResource = (UrlResource) ks.getResources()
                .newUrlResource(url);
        urlResource.setUsername("admin");
        urlResource.setPassword("admin");
        urlResource.setBasicAuthentication("enabled");
        InputStream is = urlResource.getInputStream();

       KieModule kModule = kr.addKieModule(ks.getResources()
                .newInputStreamResource(is));

        KieContainer kContainer = ks.newKieContainer(kModule.getReleaseId());
        KieBase kBase = kContainer.getKieBase();

        System.out.println(kBase.getKiePackages().toString());
        System.out.println(kBase.getKiePackage("com.epam.drools").getRules());

        KieSession kSession = kBase.newKieSession();

        kSession.insert(object);



        kSession.fireAllRules();


    }

}
