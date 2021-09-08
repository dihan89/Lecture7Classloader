

import java.net.MalformedURLException;

public class Main {

    public static void main(String[] args) throws MalformedURLException {
        PluginManager pluginManager = new PluginManager("/Users/u17724333/Job/JavaШкола/ClassLoader/java-school/plugin-manager/plugins");
        System.out.println("Init");
        pluginManager.initializePlugin();
        pluginManager.startAll();
    }
}
