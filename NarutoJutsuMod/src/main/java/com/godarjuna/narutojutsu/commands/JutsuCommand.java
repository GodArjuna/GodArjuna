package com.godarjuna.narutojutsu.commands;

import com.godarjuna.narutojutsu.core.IJutsu;
import com.godarjuna.narutojutsu.core.JutsuRegistry;
import com.godarjuna.narutojutsu.handlers.PlayerJutsuData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JutsuCommand extends CommandBase {
    
    @Override
    public String getCommandName() {
        return "jutsu";
    }
    
    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/jutsu <give|list|use> [jutsu] [player]";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2; // Requer permissões de operador
    }
    
    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.addChatMessage(new ChatComponentText("Uso: " + getCommandUsage(sender)));
            return;
        }
        
        String subCommand = args[0].toLowerCase();
        
        switch (subCommand) {
            case "give":
                handleGive(sender, args);
                break;
            case "list":
                handleList(sender);
                break;
            case "use":
                handleUse(sender, args);
                break;
            default:
                sender.addChatMessage(new ChatComponentText("Subcomando desconhecido: " + subCommand));
                sender.addChatMessage(new ChatComponentText("Uso: " + getCommandUsage(sender)));
                break;
        }
    }
    
    private void handleGive(ICommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.addChatMessage(new ChatComponentText("Uso: /jutsu give <jutsu> <player>"));
            return;
        }
        
        String jutsuName = args[1];
        String playerName = args[2];
        
        // Verificar se o jutsu existe
        if (!JutsuRegistry.hasJutsu(jutsuName)) {
            sender.addChatMessage(new ChatComponentText("§cJutsu não encontrado: " + jutsuName));
            sender.addChatMessage(new ChatComponentText("§eUse /jutsu list para ver os jutsus disponíveis"));
            return;
        }
        
        // Encontrar o jogador
        EntityPlayer player = MinecraftServer.getServer().getConfigurationManager().func_152612_a(playerName);
        
        if (player == null) {
            sender.addChatMessage(new ChatComponentText("§cJogador não encontrado: " + playerName));
            return;
        }
        
        // Dar o jutsu ao jogador
        PlayerJutsuData.giveJutsu(player, jutsuName);
        
        sender.addChatMessage(new ChatComponentText("§aJutsu §e" + jutsuName + "§a dado ao jogador §e" + playerName));
        player.addChatMessage(new ChatComponentText("§aVocê recebeu o jutsu: §e" + jutsuName));
    }
    
    private void handleList(ICommandSender sender) {
        sender.addChatMessage(new ChatComponentText("§6=== Jutsus Disponíveis ==="));
        
        Map<String, IJutsu> jutsus = JutsuRegistry.getAllJutsus();
        
        for (Map.Entry<String, IJutsu> entry : jutsus.entrySet()) {
            IJutsu jutsu = entry.getValue();
            sender.addChatMessage(new ChatComponentText(
                "§e" + jutsu.getName() + "§f - " + jutsu.getDescription()
            ));
            sender.addChatMessage(new ChatComponentText(
                "  §7Dano: §c" + jutsu.getDamage() + 
                " §7| Cooldown: §b" + (jutsu.getCooldown() / 20) + "s"
            ));
        }
    }
    
    private void handleUse(ICommandSender sender, String[] args) {
        if (!(sender instanceof EntityPlayer)) {
            sender.addChatMessage(new ChatComponentText("§cEste comando só pode ser usado por jogadores"));
            return;
        }
        
        if (args.length < 2) {
            sender.addChatMessage(new ChatComponentText("Uso: /jutsu use <jutsu>"));
            return;
        }
        
        EntityPlayer player = (EntityPlayer) sender;
        String jutsuName = args[1];
        
        // Verificar se o jogador possui o jutsu
        if (!PlayerJutsuData.hasJutsu(player, jutsuName)) {
            player.addChatMessage(new ChatComponentText("§cVocê não possui este jutsu!"));
            return;
        }
        
        // Verificar cooldown
        if (PlayerJutsuData.isOnCooldown(player, jutsuName)) {
            int remainingTicks = PlayerJutsuData.getRemainingCooldown(player, jutsuName);
            player.addChatMessage(new ChatComponentText("§cJutsu em cooldown! Aguarde " + (remainingTicks / 20) + " segundos"));
            return;
        }
        
        // Executar o jutsu
        IJutsu jutsu = JutsuRegistry.getJutsu(jutsuName);
        if (jutsu != null) {
            if (jutsu.execute(player.worldObj, player)) {
                PlayerJutsuData.setCooldown(player, jutsuName, jutsu.getCooldown());
                player.addChatMessage(new ChatComponentText("§aUsou: §e" + jutsu.getName()));
            } else {
                player.addChatMessage(new ChatComponentText("§cFalha ao usar o jutsu!"));
            }
        }
    }
    
    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        List<String> options = new ArrayList<String>();
        
        if (args.length == 1) {
            options.add("give");
            options.add("list");
            options.add("use");
        } else if (args.length == 2 && (args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("use"))) {
            // Adicionar nomes de jutsus
            for (String jutsuName : JutsuRegistry.getAllJutsus().keySet()) {
                options.add(jutsuName);
            }
        } else if (args.length == 3 && args[0].equalsIgnoreCase("give")) {
            // Adicionar nomes de jogadores
            return MinecraftServer.getServer().getAllUsernames();
        }
        
        return options;
    }
}
