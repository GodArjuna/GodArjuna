# 🎮 Installation Guide - Minecraft Anime Skills System

## Quick Start Guide

Follow these steps to install and configure the Anime Skills system on your Minecraft 1.7.10 server.

### 📋 Prerequisites

- ✅ Minecraft Server 1.7.10 (Spigot or Bukkit)
- ✅ Skript 2.2 or higher
- ✅ Basic knowledge of server management
- ✅ File access to your server (FTP, SFTP, or direct access)

### 🔧 Step-by-Step Installation

#### Step 1: Install Skript

1. Download Skript 2.2 for Minecraft 1.7.10
   - Visit: https://github.com/SkriptLang/Skript/releases
   - Download the appropriate version

2. Place the Skript JAR file in your server's `plugins/` folder

3. Restart your server

4. Verify installation:
   ```
   /skript info
   ```

#### Step 2: Install Anime Skills System

1. **Locate your Skript scripts folder:**
   ```
   /server/plugins/Skript/scripts/
   ```

2. **Copy the core system file:**
   ```bash
   # Copy the main system file
   cp minecraft-anime-skills/scripts/anime-skills-core.sk /server/plugins/Skript/scripts/
   ```

3. **Copy the skill files:**
   ```bash
   # Copy all individual skill files
   cp minecraft-anime-skills/skills/*.sk /server/plugins/Skript/scripts/
   ```

#### Step 3: Configure Permissions

Add these permissions to your permissions plugin (PermissionsEx, LuckPerms, etc.):

**For players:**
```yaml
# Basic access
- animeskills.use

# Naruto skills
- animeskills.naruto.rasengan
- animeskills.naruto.chidori
- animeskills.naruto.shadow-clone

# One Piece skills
- animeskills.one-piece.gomu-pistol

# Dragon Ball skills
- animeskills.dragon-ball.kamehameha

# My Hero Academia skills
- animeskills.my-hero-academia.one-for-all
```

**For admins:**
```yaml
# Admin access
- animeskills.admin
- animeskills.*
```

#### Step 4: Reload Skript

1. **Reload all scripts:**
   ```
   /skript reload all
   ```

2. **Or reload individually:**
   ```
   /skript reload anime-skills-core.sk
   /skript reload naruto_rasengan.sk
   /skript reload naruto_chidori.sk
   ```

3. **Check for errors:**
   ```
   /skript list
   ```

### ✅ Verification

Test if the system is working:

1. **Check system status:**
   ```
   /animeskills info
   ```

2. **View available skills:**
   ```
   /skills
   ```

3. **Check your mana:**
   ```
   /mana
   ```

4. **Test a skill:**
   ```
   /rasengan
   ```

### 🎯 Configuration (Optional)

The system works out of the box, but you can customize it:

1. **Edit skill values in each .sk file:**
   - Mana costs
   - Cooldowns
   - Damage values
   - Particle effects

2. **Example customization (naruto_rasengan.sk):**
   ```skript
   # Change mana cost from 40 to 30
   if {player_mana_%player%} >= 30:
   
   # Change cooldown from 8 to 5 seconds
   cooldown: 5 seconds
   
   # Change damage from 12 to 15 hearts
   damage {_target} by 15 hearts
   ```

### 🐛 Troubleshooting

#### Problem: Skills don't work

**Solution 1:** Check permissions
```
/lp user <player> permission check animeskills.naruto.rasengan
```

**Solution 2:** Verify Skript is loaded
```
/plugins
# Should show Skript in green
```

**Solution 3:** Check for script errors
```
/skript list
# Look for red errors
```

#### Problem: Mana doesn't regenerate

**Solution:** Reload the core system
```
/skript reload anime-skills-core.sk
```

#### Problem: Particles don't show

**Solution:** This is normal for some Minecraft 1.7.10 versions. Some particle effects may not be available. You can modify the particle types in the skill files.

#### Problem: Commands not recognized

**Solution:** Check if scripts are enabled
```
/skript enable anime-skills-core.sk
/skript reload anime-skills-core.sk
```

### 🎨 Customization Guide

#### Adding New Skills

1. Use the AI generator (recommended):
   ```bash
   cd minecraft-anime-skills/ai-integration/
   python3 skill_generator.py
   ```

2. Or copy an existing skill and modify it:
   ```bash
   cp naruto_rasengan.sk my_custom_skill.sk
   # Edit the file
   # Reload: /skript reload my_custom_skill.sk
   ```

#### Adjusting Balance

Edit these values in skill files:

```skript
# Mana cost
if {player_mana_%player%} >= 40:  # Change this number

# Cooldown
cooldown: 8 seconds  # Change this duration

# Damage
damage {_target} by 12 hearts  # Change this value

# Range
set {_target} to target entity of player within 10 blocks  # Change range
```

### 📊 Server Performance

The system is optimized for performance, but here are some tips:

1. **Reduce particle effects** if server lags:
   ```skript
   # Comment out or reduce particle loops
   # loop 20 times:  # Change to loop 10 times
   ```

2. **Increase cooldowns** for popular skills

3. **Limit concurrent skill usage** per player

### 🔐 Security Recommendations

1. **Limit admin permissions:**
   - Only give `animeskills.admin` to trusted staff

2. **Balance skill power:**
   - Test in creative mode first
   - Adjust values based on your server's gameplay

3. **Monitor usage:**
   - Check server logs for abuse
   - Set up anti-cheat if needed

### 📱 Multi-World Support

To enable/disable skills in specific worlds:

```skript
# Add this at the start of each command:
command /rasengan:
    trigger:
        if world of player is "world_pvp":
            # Skill code here
        else:
            send "§cSkills are disabled in this world!"
            stop
```

### 🎯 Economy Integration

To charge money for skill usage:

```skript
# Requires Vault plugin
command /rasengan:
    trigger:
        if player's balance >= 100:
            remove 100 from player's balance
            # Skill code here
        else:
            send "§cYou need $100 to use this skill!"
```

### 🌐 Language Customization

All messages are in Portuguese (PT-BR) by default. To change to English:

1. Find all `send "§..."` lines
2. Replace Portuguese text with English
3. Example:
   ```skript
   # Before
   send "§a[AnimeSkills] Mana insuficiente!"
   
   # After
   send "§c[AnimeSkills] Insufficient mana!"
   ```

### 📞 Support

If you need help:

1. Check the main README.md
2. Read COPILOT_GUIDE.md for AI integration
3. Check Skript documentation
4. Open an issue on GitHub

### ✨ Next Steps

After installation:

1. ✅ Test all skills in creative mode
2. ✅ Balance values for your server
3. ✅ Train staff on how the system works
4. ✅ Create custom skills with the AI generator
5. ✅ Share feedback and improvements!

---

**🎮 Enjoy your new Anime Skills system!**
