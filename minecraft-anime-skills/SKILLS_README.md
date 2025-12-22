# Skills Directory Structure

## Directory Organization

The system has two directories for skill files:

### 📁 `skills/` - Production-Ready Skills
High-quality, hand-crafted skills ready for use in production servers:
- `naruto_rasengan.sk` - Rasengan attack (40 mana, 8s cooldown)
- `naruto_chidori.sk` - Chidori lightning attack (50 mana, 10s cooldown)
- `naruto_shadow_clone.sk` - Shadow Clone buff (60 mana, 30s cooldown)
- `dragonball_kamehameha.sk` - Kamehameha energy beam (70 mana, 15s cooldown)
- `onepiece_gomu_pistol.sk` - Gomu Gomu no Pistol (25 mana, 5s cooldown)
- `mha_one_for_all.sk` - One For All ultimate buff (80 mana, 60s cooldown)

**✅ Use these files for production servers!**

### 📁 `scripts/` - Core System
Contains the main core system file:
- `anime-skills-core.sk` - Main system (mana, combos, levels, etc.)

**Note:** The AI generator creates skill files but they need manual review before use. The files in `skills/` are production-tested and balanced.

## Installation

Copy both directories to your server:

```bash
# Copy core system
cp minecraft-anime-skills/scripts/anime-skills-core.sk /server/plugins/Skript/scripts/

# Copy production skills
cp minecraft-anime-skills/skills/*.sk /server/plugins/Skript/scripts/
```

## Using the AI Generator

The AI generator in `ai-integration/skill_generator.py` creates skill templates that:
- Provide a starting point for new skills
- Need manual review and testing
- May require syntax adjustments

For production use, the manually crafted skills in `skills/` are recommended.
