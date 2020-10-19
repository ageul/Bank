package world.bentobox.bank.commands.admin;

import java.util.List;

import world.bentobox.bank.Bank;
import world.bentobox.bank.data.TxType;
import world.bentobox.bentobox.api.commands.CompositeCommand;
import world.bentobox.bentobox.api.localization.TextVariables;
import world.bentobox.bentobox.api.user.User;
import world.bentobox.bentobox.hooks.VaultHook;

/**
 * @author tastybento
 *
 */
public class AdminTakeCommand extends AdminCommand {

    public AdminTakeCommand(CompositeCommand parent) {
        super(parent, "take");
    }

    @Override
    public void setup() {
        this.setPermission("bank.admin.take");
        this.setParametersHelp("bank.admin.take.parameters");
        this.setDescription("bank.admin.take.description");
    }

    @Override
    public boolean canExecute(User user, String label, List<String> args) {
        return checkArgs(user, args, 2);
    }

    @Override
    public boolean execute(User user, String label, List<String> args) {
        // Success
        ((Bank)getAddon()).getBankManager().withdraw(user, island, value, TxType.TAKE).thenAccept(result -> {
            switch (result) {
            case FAILURE_LOW_BALANCE:
                user.sendMessage("bank.errors.low-balance");
                break;
            case SUCCESS:
                VaultHook vault = ((Bank)this.getAddon()).getVault();
                user.sendMessage("bank.withdraw.success", TextVariables.NUMBER, vault.format(((Bank)getAddon()).getBankManager().getBalance(user, getWorld())));
                break;
            default:
                user.sendMessage("bank.errors.bank-error");
                break;

            }
        });
        return true;
    }

}