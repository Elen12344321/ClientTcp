package commands;

public enum TCP_commands {
    CMD_PING("^#(ping):?"),
    CMD_ECHO("^#(echo):?"),
    EXIT("^#(exit):");

    private final String reg;

    //construtor
    TCP_commands(final String reg) {
        this.reg = reg;
    }

    public String getReg() {
        return reg;
    }
}

