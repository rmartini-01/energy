package models;

public enum Role {
    SOURCE {
        @Override
        public String toString() {
            return "Source";
        }
    },

    LAMP {
        @Override
        public String toString() {
            return "Lamp";
        }
    },
    TERMINAL {
        @Override
        public String toString() {
            return "Terminal";
        }
    },
    EMPTY {
        @Override
        public String toString() {
            return "Empty";
        }
    }
};