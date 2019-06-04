package commands;

public enum TCP_commands {
    CMD_PING("^%(ping):?"),
    CMD_ECHO("^%(echo):?\\s(.)+;"),
    CMD_LOGIN("^%(log):?\\s((l)(-)(.)+)\\s((p)(-)(.)+)+;"),
    CMD_LIST("^%list:?"),
    CMD_MSG("^%msg:?\\s((l)(-)(.)+)\\s((t)(-)(.)+)+;"),
    CMD_FILE("^%(file):?\\s((log)(-)(.)+)\\s((f)(-)(.)+)+;"),
    CMD_EXIT("^%(ex):?");


    private final String reg;

    //construtor
    TCP_commands(final String reg) {
        this.reg = reg;
    }

    public String getReg() {
        return reg;
    }
}

