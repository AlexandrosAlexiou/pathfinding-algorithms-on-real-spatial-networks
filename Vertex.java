public class Vertex {
    private final String id;
    private final Float longitude;
    private final Float latitude;

    public Vertex(String id, Float longitude, Float latitude ) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getId() {
        return id;
    }

    public Float getLongitude() {
        return this.longitude;
    }

    public Float getLatitude() {
        return this.latitude;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vertex other = (Vertex) obj;
        if (id == null) {
            return other.id == null;
        } else return id.equals(other.id);
    }

    @Override
    public String toString() {
        return this.id + " " + getLongitude() + " " + getLatitude();
    }
}
