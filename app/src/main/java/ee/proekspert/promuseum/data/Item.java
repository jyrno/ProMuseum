package ee.proekspert.promuseum.data;

public class Item {

    private final String id;
    private final String museumId;
    private String name;
    private String description;
    private String damage;
    private ItemStatus status;
    private ItemState state;
    private ItemLocation location;

    public Item(String id, String museumId, String name, String description, String damage, ItemStatus status, ItemState state, ItemLocation location) {
        this.id = id;
        this.museumId = museumId;
        this.name = name;
        this.description = description;
        this.damage = damage;
        this.status = status;
        this.state = state;
        this.location = location;
    }

    public String getMuseumId() {
        return museumId;
    }

    public enum ItemStatus {
        CHECKED(true),
        UNCHECKED(false),
        LOST;

        private final boolean checked;
        private final boolean lost;

        public boolean isChecked() {
            return checked;
        }

        public boolean isLost(){
            return lost;
        }

        ItemStatus(boolean checked) {
            this.checked = checked;
            this.lost = false;
        }

        ItemStatus() {
            this.checked = false;
            this.lost = true;
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDamage() {
        return damage;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public ItemState getState() {
        return state;
    }

    public ItemLocation getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", damage='" + damage + '\'' +
                ", status=" + status +
                ", state=" + state +
                ", location=" + location +
                '}';
    }

    public enum ItemState {
        // TODO take string from the translations or at least put estonian strings
        VERY_GOOD{
            @Override
            public String toString() {
                return "Very good";
            }
        },
        GOOD{
            @Override
            public String toString() {
                return "Good";
            }
        },
        SATISFACTORY{
            @Override
            public String toString() {
                return "Satisfactory";
            }
        },
        BAD{
            @Override
            public String toString() {
                return "Bad";
            }
        },
        VERY_BAD{
            @Override
            public String toString() {
                return "Very bad";
            }
        }
    }

    public static class ItemLocation {
        private String locationName;

        public ItemLocation(String locationName) {
            this.locationName = locationName;
        }

        @Override
        public int hashCode() {
            return locationName == null ? 0 : locationName.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            String objLocationName;
            return obj instanceof ItemLocation
                    && this.locationName != null
                    && ((objLocationName = ((ItemLocation) obj).locationName) != null)
                    && objLocationName == this.locationName;
        }

        @Override
        public String toString() {
            return locationName != null ? locationName : "";
        }
    }

}
