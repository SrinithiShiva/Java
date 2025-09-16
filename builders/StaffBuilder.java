package builders;

import members.MemberType;

public class StaffBuilder extends MemberBuilder{
    public StaffBuilder(){
        super(5,30.0, MemberType.STAFF);
    }
}
