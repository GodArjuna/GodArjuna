# 🤝 Contributing to Minecraft Anime Skills

Thank you for your interest in contributing! This guide will help you get started.

## 🎯 Ways to Contribute

### 1. 📝 Add New Skills

Create skills from other anime series:

```bash
# Use the AI generator
cd minecraft-anime-skills/ai-integration/
python3 skill_generator.py

# Or create manually
cp skills/naruto_rasengan.sk skills/your_anime_skill.sk
# Edit and customize
```

**Popular Anime to Add:**
- ✨ Bleach (Getsuga Tenshou, Bankai, etc.)
- ⚔️ Attack on Titan (ODM Gear, Thunder Spear)
- 🔥 Demon Slayer (Water Breathing, Hinokami Kagura)
- 🌟 JoJo's Bizarre Adventure (Star Platinum, Time Stop)
- 👊 One Punch Man (Serious Punch, Consecutive Normal Punches)
- 🧊 Fairy Tail (Fire Dragon Roar, Ice Make)
- ⚡ Black Clover (Anti-Magic, Dark Magic)

### 2. 🐛 Report Bugs

Found a bug? Open an issue with:
- Description of the problem
- Steps to reproduce
- Expected vs actual behavior
- Minecraft version, Skript version
- Error logs (if any)

### 3. 💡 Suggest Features

Have ideas? We'd love to hear them!
- New anime sources
- Gameplay mechanics
- Balance improvements
- Visual enhancements

### 4. 📚 Improve Documentation

Help others by:
- Fixing typos
- Adding examples
- Translating to other languages
- Creating video tutorials

### 5. 🤖 Enhance AI Integration

Improve the AI generator:
- Better skill templates
- More generation options
- Integration with other AI services
- Automated balancing

## 🔧 Development Setup

### Prerequisites

```bash
# Install Python 3.8+
python3 --version

# Clone repository
git clone https://github.com/GodArjuna/GodArjuna.git
cd GodArjuna/minecraft-anime-skills
```

### Testing Skills

1. Set up a Minecraft 1.7.10 test server
2. Install Skript 2.2+
3. Copy skill files to `plugins/Skript/scripts/`
4. Test in creative mode first
5. Verify balance and effects

## 📋 Contribution Guidelines

### Code Style

**For Skript files (.sk):**
```skript
# Always include header comment
# Skill Name - Anime
# Description of what it does
# Mana: X | Cooldown: Ys | Damage: Z

command /skillname:
    permission: animeskills.anime.skillname
    cooldown: X seconds
    trigger:
        # Clear, commented code
        if {player_mana_%player%} >= COST:
            # Skill logic here
```

**For Python files (.py):**
```python
"""
Module docstring explaining purpose
"""

def function_name(param: type) -> type:
    """Function docstring"""
    # Clear, commented code
```

### Skill Balance Guidelines

Follow these ranges for balance:

**Attack Skills:**
- Mana: 20-80
- Cooldown: 5-20 seconds
- Damage: 5-20 hearts
- Range: 5-20 blocks

**Buff Skills:**
- Mana: 40-100
- Cooldown: 20-60 seconds
- Duration: 10-30 seconds

**Movement Skills:**
- Mana: 15-50
- Cooldown: 5-15 seconds
- Distance: 5-30 blocks

**Ultimate Skills:**
- Mana: 80-150
- Cooldown: 45-120 seconds
- High impact but fair

### Commit Messages

Use clear, descriptive commits:

```bash
# Good examples:
git commit -m "Add Bleach Getsuga Tenshou skill"
git commit -m "Fix Rasengan particle effects"
git commit -m "Balance Kamehameha cooldown from 20s to 15s"
git commit -m "Add documentation for combo system"

# Bad examples:
git commit -m "Update"
git commit -m "Fix stuff"
git commit -m "Changes"
```

### Pull Request Process

1. **Fork the repository**
2. **Create a feature branch**
   ```bash
   git checkout -b feature/bleach-skills
   ```

3. **Make your changes**
   - Add/modify skills
   - Test thoroughly
   - Update documentation

4. **Commit your changes**
   ```bash
   git add .
   git commit -m "Add Bleach skills with AI generation"
   ```

5. **Push to your fork**
   ```bash
   git push origin feature/bleach-skills
   ```

6. **Open a Pull Request**
   - Describe what you added/changed
   - Include screenshots/videos if applicable
   - Reference any related issues

## ✅ Checklist for New Skills

Before submitting a new skill, verify:

- [ ] Skill has clear header comments
- [ ] Permission is properly set
- [ ] Mana cost is balanced (20-100 range)
- [ ] Cooldown is appropriate (5-60s range)
- [ ] Damage is fair (5-20 hearts)
- [ ] Visual effects are present
- [ ] Sound effects work
- [ ] No console errors
- [ ] Tested in multiplayer
- [ ] Documentation updated

## 🎨 Adding Visual Effects

### Particle Effects (Minecraft 1.7.10)

Available particles:
```skript
play "EXPLOSION_LARGE" at location
play "EXPLOSION_HUGE" at location
play "FIREWORKS_SPARK" at location
play "FLAME" at location
play "CLOUD" at location
play "CRIT" at location
play "CRIT_MAGIC" at location
play "SPELL_INSTANT" at location
play "SPELL_WITCH" at location
play "VILLAGER_HAPPY" at location
play "ENCHANTMENT_TABLE" at location
play "LAVA_POP" at location
```

### Sound Effects

Available sounds:
```skript
play sound "EXPLODE" to player
play sound "ENDERDRAGON_GROWL" to player
play sound "LEVEL_UP" to player
play sound "AMBIENCE_THUNDER" to player
play sound "FIZZ" to player
play sound "ZOMBIE_REMEDY" to player
```

## 🌐 Internationalization

To add support for other languages:

1. Create language file: `config/lang_en.yml`
2. Add translations
3. Update skill messages to use translation keys

Example:
```yaml
# lang_en.yml
skills:
  rasengan:
    name: "Rasengan"
    activated: "&a[AnimeSkills] Rasengan activated!"
    insufficient_mana: "&c[AnimeSkills] Insufficient chakra! Required: 40"
```

## 🏆 Recognition

Contributors will be:
- Listed in CONTRIBUTORS.md
- Mentioned in release notes
- Credited in skill files (for major contributions)

## ❓ Questions?

- Open an issue for questions
- Check existing documentation
- Contact: [LinkedIn](https://www.linkedin.com/in/kawan-villar-6306b7285/)

## 📜 Code of Conduct

- Be respectful and inclusive
- Provide constructive feedback
- Help others learn
- Have fun! 🎮

---

**Thank you for contributing to Minecraft Anime Skills!** ⚡

Your contributions help make this project better for everyone in the community.
