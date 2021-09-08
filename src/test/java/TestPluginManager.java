import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

public class TestPluginManager {


  @Test
   public void testHelloWord() throws MalformedURLException {
        PluginManager pm = new PluginManager("plugins");
        pm.initializePlugin();
        pm.startAll();
    }
}
