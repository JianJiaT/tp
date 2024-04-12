package brokeculator.command;

import brokeculator.dashboard.Dashboard;
import brokeculator.frontend.UI;

public abstract class Command {
    public Command() {}
    public abstract void execute(Dashboard dashboard, UI ui);
}
