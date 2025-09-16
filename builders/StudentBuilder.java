package builders;

import members.MemberType;

public class StudentBuilder extends MemberBuilder{
    public StudentBuilder(){
        super(4,20.0,MemberType.STUDENT);
    }
}
