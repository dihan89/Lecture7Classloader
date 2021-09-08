import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class PluginManager {
    private final File pluginRootDirectory;
    private final List<Plugin> plugins = new LinkedList<>();
    private  ClassLoader pluginLoader;

    public PluginManager(String pluginRootDirectory) {
        File rootDir = new File(pluginRootDirectory);
       // System.out.println(rootDir.getAbsolutePath());
        if (!rootDir.isDirectory()) {
            throw new IllegalArgumentException("Переданный путь не является директорией");
        }
        this.pluginRootDirectory = rootDir;

     //   File file = new File(rootDir + "/HelloWorldPlugin");
       // pluginLoader = new URLClassLoader(new URL[]{file.toURI().toURL()});
    }

    /**
     *
     * @param pluginName
     * @param pluginClassName
     * @return null if plugin not found
     */
    public ArrayList<Plugin> load(String pluginName, String pluginClassName) {
          try {
              File dir = new File(pluginRootDirectory.getAbsolutePath()+"/"+ pluginName);
              ArrayList <Plugin> plugs= new ArrayList<>();
              if(dir == null)
                  return plugs;
              List<File> files =
                      Arrays.stream(dir.listFiles()).filter(f -> f.getName().endsWith(".class")).collect(Collectors.toList());
              URL[] urls = new URL[files.size()];
              for (int i = 0; i < files.size(); ++i)
                  urls[i] = files.get(i).toURI().toURL();
              pluginLoader = new URLClassLoader(urls);

              for (File file : files)
              plugs.add((Plugin) pluginLoader.loadClass(file.getName().replace(".class","")).getConstructor().newInstance());
              return plugs;
        } catch (ClassNotFoundException | InstantiationException | InvocationTargetException | NoSuchMethodException |
                  IllegalAccessException | MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Инициализирует все плагины из pluginRootDirectory
     */
    public void initializePlugin() {
        List<File> allPlugins = getAllPath();//DIRECTORIES
        for (File dir : allPlugins) {
            String pluginName = dir.getName();
            plugins.addAll(load(pluginName, pluginName));
        }
    }

    /**
     * Запускает все имеющиеся плагины.  т.е вызывает метод doUsefull
     */
    public void startAll() {
        for (Plugin plugin : plugins) {
            plugin.doUsefull();
        }
    }

    /**
     * @return все директории плагинов в рут директории
     */
    private List<File> getAllPath() {
        File[] files = pluginRootDirectory.listFiles();
        //for (File file : files) System.out.println(file.getAbsolutePath());
        assert files != null;
        return Arrays.stream(files).filter(File::isDirectory).collect(Collectors.toList());
    }
}

