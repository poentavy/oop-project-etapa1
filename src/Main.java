import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import implementation.Database;

import java.io.File;

public final class Main {
    private Main() { }

    /**
     * main
     * @param args arguments
     */
    public static void main(final String[] args) {
        ObjectMapper mp = new ObjectMapper();
        ObjectWriter writer = mp.writerWithDefaultPrettyPrinter();
        ObjectNode input = null;
        ArrayNode output = mp.createArrayNode();
        try {
            input = mp.readValue(new File(args[0]), ObjectNode.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Database.getInstance().init(input, output);
            Database.getInstance().run();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            writer.writeValue(new File(args[1]), output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
