package commands;

public enum TCP_commands {
    CMD_PING("^%(ping):?"),
    CMD_ECHO("^%(echo):?"),
    CMD_LOGIN("^%(log)?:((log)(-)(.)+)(\\s)((pass)+(-)+(.)+)+;"),
    EXIT("^#(e|exit):");


    private final String reg;

    //construtor
    TCP_commands(final String reg) {
        this.reg = reg;
    }

    public String getReg() {
        return reg;
    }
}

