import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class SearchEngineHandler implements URLHandler {
    ArrayList<String> strings;

    SearchEngineHandler() {
        strings = new ArrayList<String>();
    }

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            String output = "Here's the list of strings so far: \n";
            for (int i = 0; i < strings.size(); i++) {
                output += " * " + strings.get(i) + "\n";
            }
            return output;

        } else if (url.getPath().contains("/search")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                String query = parameters[1];
                String output = "";
                for (int i = 0; i < strings.size(); i++) {
                    if(strings.get(i).contains(query)) {
                        output += strings.get(i) + "\n";
                    }
                }
                return output;
            }
        } else if (url.getPath().contains("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                strings.add(parameters[1]);
                return String.format("Added %s to list of strings.", parameters[1]);
            }
        }
        else {
            System.out.println("Path: " + url.getPath());
            return "404 Not Found!";
        }
        return "";
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new SearchEngineHandler());
    }
}
