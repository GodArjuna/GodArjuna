# ⚡ Quick Start Guide - Minecraft Anime Skills

Get started with the Anime Skills system in 5 minutes!

## 🚀 Installation (3 steps)

### 1. Prerequisites
```bash
# You need:
- Minecraft 1.7.10 Server (Spigot/Bukkit)
- Skript 2.2+ plugin installed
```

### 2. Install Skills
```bash
# Copy files to your server
cp minecraft-anime-skills/scripts/*.sk /your-server/plugins/Skript/scripts/
cp minecraft-anime-skills/skills/*.sk /your-server/plugins/Skript/scripts/
```

### 3. Reload
```bash
# In-game command:
/skript reload all
```

## 🎮 Basic Usage

### For Players

**Check your skills:**
```
/skills
```

**Check your mana:**
```
/mana
```

**Use a skill:**
```
/rasengan
/kamehameha
/shadowclone
```

**Get help:**
```
/animeskills-help
```

### For Admins

**System info:**
```
/animeskills info
```

**List all skills:**
```
/animeskills list
```

**Reload system:**
```
/animeskills reload
```

## 🎯 Available Skills

| Skill | Command | Anime | Cost | Cooldown |
|-------|---------|-------|------|----------|
| Rasengan | `/rasengan` | Naruto | 40 mana | 8s |
| Chidori | `/chidori` | Naruto | 50 mana | 10s |
| Shadow Clone | `/shadowclone` | Naruto | 60 mana | 30s |
| Kamehameha | `/kamehameha` | Dragon Ball | 70 mana | 15s |
| Gomu Gomu Pistol | `/gomupistol` | One Piece | 25 mana | 5s |
| One For All | `/oneforall` | My Hero Academia | 80 mana | 60s |

## 🔐 Permissions

**Give all skills to a player:**
```
/lp user <player> permission set animeskills.*
```

**Give specific anime skills:**
```
/lp user <player> permission set animeskills.naruto.*
/lp user <player> permission set animeskills.dragon-ball.*
```

**Give single skill:**
```
/lp user <player> permission set animeskills.naruto.rasengan
```

## 🤖 Generate New Skills with AI

### Using Python Generator

```bash
cd minecraft-anime-skills/ai-integration/
python3 skill_generator.py
```

### Using GitHub Copilot

1. Open VS Code with Copilot extension
2. Create new `.sk` file
3. Write comments describing the skill:

```skript
# Create a Bleach skill called Getsuga Tenshou
# Fires a wave of energy in a straight line
# Damage: 10 hearts, Range: 15 blocks
# Mana cost: 55, Cooldown: 12 seconds

command /getsuga:
    # Copilot will suggest the code here!
```

See full guide: [COPILOT_GUIDE.md](docs/COPILOT_GUIDE.md)

## 💡 Tips

1. **Mana Management**
   - Regenerates 5 points every 5 seconds
   - Use `/mana` to check current amount

2. **Combos**
   - Use skills within 5 seconds of each other
   - Get +50% damage bonus!

3. **Leveling**
   - Use skills to gain XP
   - Each level = +10 max mana

4. **Customization**
   - Edit `.sk` files to change values
   - Adjust damage, cost, cooldown as needed

## 📚 Full Documentation

- [README.md](README.md) - Complete documentation
- [INSTALLATION.md](docs/INSTALLATION.md) - Detailed installation
- [COPILOT_GUIDE.md](docs/COPILOT_GUIDE.md) - AI integration guide

## 🐛 Troubleshooting

**Skills don't work?**
- Check permissions: `/lp user <player> permission check animeskills.naruto.rasengan`
- Verify Skript loaded: `/plugins`

**Mana not regenerating?**
- Reload core: `/skript reload anime-skills-core.sk`

**Need help?**
- Read full [INSTALLATION.md](docs/INSTALLATION.md)
- Check Skript console for errors

## 🎉 You're Ready!

Now you can:
- ✅ Use anime skills in Minecraft
- ✅ Create custom skills with AI
- ✅ Share with your friends

**Have fun!** 🎮⚡

---

Made with ❤️ by Kawan Villar | Powered by GitHub Copilot
