package commands;

public enum TCP_commands {
    CMD_PING("^%(ping):?"),
    CMD_ECHO("^%(echo):?"),
    CMD_LOGIN("^%(log)?:((log)(-)(.)+)(\\s)((pass)+(-)+(.)+)+;"),
    CMD_LIST("^%list:?"),
    CMD_MSG("^%(msg):?\\s((log)(-)(.)+)\\s((m)(-)(.)+)+;"),
    CMD_RECIVE_MSG("^%(rec):?"),
    CMD_FILE("^%(file):?\\s(file)(-)(.)+;"),
    CMD_RECIVE_FILE("^%(recf):?"),
        EXIT("^%(ex):?");


    private final String reg;

    //construtor
    TCP_commands(final String reg) {
        this.reg = reg;
    }

    public String getReg() {
        return reg;
    }
}

