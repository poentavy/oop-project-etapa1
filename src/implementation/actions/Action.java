package implementation.actions;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface Action {
    /**
     * Run action
     * @param output output json node
     */
    void run(ObjectNode output);
}
