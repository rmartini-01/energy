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
    WIFI {
        @Override
        public String toString() {
            return "Wifi";
        }
    },
    EMPTY {
        @Override
        public String toString() {
            return "Empty";
        }
    }
};