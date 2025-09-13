package members;

public enum MemberType
{
    STUDENT("student"),
    FACULTY("faculty"),
    GENERAL_PUBLIC("Public");
    private String name;
    MemberType(String name){
        this.name=name;
    }
    public String getName() {
        return name;
    }
}
