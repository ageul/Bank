package world.bentobox.bank.commands.admin;

import java.util.List;

import world.bentobox.bank.BankResponse;
import world.bentobox.bank.data.TxType;
import world.bentobox.bentobox.api.commands.CompositeCommand;
import world.bentobox.bentobox.api.localization.TextVariables;
import world.bentobox.bentobox.api.user.User;

/**
 * @author tastybento
 *
 */
public class AdminSetCommand extends AbstractAdminBankCommand {

    public AdminSetCommand(CompositeCommand parent) {
        super(parent, "set");
    }

    @Override
    public void setup() {
        this.setPermission("bank.admin.set");
        this.setParametersHelp("bank.admin.set.parameters");
        this.setDescription("bank.admin.set.description");
    }

    @Override
    public boolean canExecute(User user, String label, List<String> args) {
        return checkArgs(user, args, RequestType.ADMIN_SET);
    }

    @Override
    public boolean execute(User user, String label, List<String> args) {
        // Success
        addon
        .getBankManager()
        .set(user, island.getUniqueId(), value, value, TxType.SET)
        .thenAccept(result -> {
            if (result == BankResponse.SUCCESS) {
                user.sendMessage("bank.admin.set.success", TextVariables.NAME, target.getName(), TextVariables.NUMBER, addon.getVault().format(addon.getBankManager().getBalance(island).getValue()));
            } else {
                user.sendMessage("bank.errors.bank-error");
            }
        });
        return true;
    }

}
