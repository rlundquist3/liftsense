
package com.rileylundquist.liftsense;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class WorkoutContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<Exercise> ITEMS = new ArrayList<Exercise>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, Exercise> ITEM_MAP = new HashMap<String, Exercise>();

    /*static {
        // Add 3 sample items.
        addItem(new Exercise("1", "Item 1"));
        addItem(new Exercise("2", "Item 2"));
        addItem(new Exercise("3", "Item 3"));
    }*/

    public static void addItem(Exercise item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class Exercise {
        public String id;
        public String content;

        public Exercise(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}