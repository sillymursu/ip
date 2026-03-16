package legoat.commands;

import java.io.IOException;
import java.util.ArrayList;

import legoat.storage.Storage;
import legoat.tasktypes.Task;

/**
 * Utility for creating parser instances in command tests.
 */
final class TestParserFactory {
    /**
     * Prevents instantiation of utility class.
     */
    private TestParserFactory() {
    }

    /**
     * Creates a parser instance with stubbed storage.
     *
     * @return parser for testing commands without file I/O
     */
    static Parser newParser() {
        return new ParserStub();
    }

    /**
     * Parser stub that avoids persisting test data to disk.
     */
    private static class ParserStub extends Parser {
        private final Storage storageStub = new Storage() {
            /**
             * Disables saving data during tests.
             *
             * @param tasks list of tasks to save
             */
            @Override
            public void saveData(ArrayList<Task> tasks) throws IOException {
            }

            /**
             * Disables loading data during tests.
             *
             * @param taskHandler parser that would receive loaded tasks
             */
            @Override
            public void loadData(Parser taskHandler) {
            }
        };

        /**
         * Constructs a parser stub with an empty task list.
         */
        ParserStub() {
            super();
            getTasks().clear();
        }

        /**
         * Returns storage stub that does not perform disk operations.
         *
         * @return storage stub
         */
        @Override
        public Storage getStorage() {
            return storageStub;
        }
    }
}
