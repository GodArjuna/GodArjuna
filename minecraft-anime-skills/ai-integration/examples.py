"""
Example usage and demonstration of the Minecraft Anime Skills system
This file shows how to use the AI generator to create custom skills
"""

from skill_generator import AnimeSkillGenerator


def example_basic_generation():
    """Example 1: Basic skill generation"""
    print("\n" + "="*60)
    print("EXAMPLE 1: Basic Skill Generation")
    print("="*60)
    
    generator = AnimeSkillGenerator()
    
    # Generate a basic attack skill
    skill = generator.generate_skill_template(
        anime="bleach",
        skill_name="Getsuga Tenshou",
        skill_type="attack"
    )
    
    print("\n📝 Generated Skill Preview:")
    print(skill[:500] + "...\n")
    
    # Save it
    generator.save_skill(skill, "bleach_getsuga_tenshou.sk")


def example_natural_language():
    """Example 2: Generate from description"""
    print("\n" + "="*60)
    print("EXAMPLE 2: Natural Language Generation")
    print("="*60)
    
    generator = AnimeSkillGenerator()
    
    descriptions = [
        "Create a Demon Slayer water breathing attack skill",
        "Make a defensive skill from Attack on Titan with hardening",
        "Build a speed buff skill from One Punch Man",
    ]
    
    for desc in descriptions:
        print(f"\n🎯 Description: {desc}")
        skill = generator.generate_skill_from_description(desc)
        print("✅ Skill generated successfully!")


def example_batch_generation():
    """Example 3: Batch generation"""
    print("\n" + "="*60)
    print("EXAMPLE 3: Batch Generation")
    print("="*60)
    
    generator = AnimeSkillGenerator()
    
    # Define multiple skills at once
    skills = [
        # Bleach skills
        {
            "anime": "bleach",
            "name": "Getsuga Tenshou",
            "type": "attack"
        },
        {
            "anime": "bleach",
            "name": "Bankai",
            "type": "buff"
        },
        
        # Attack on Titan skills
        {
            "anime": "attack-on-titan",
            "name": "ODM Gear",
            "type": "movement"
        },
        {
            "anime": "attack-on-titan",
            "name": "Thunder Spear",
            "type": "attack"
        },
        
        # Demon Slayer skills
        {
            "anime": "demon-slayer",
            "name": "Water Breathing",
            "type": "attack"
        },
        {
            "anime": "demon-slayer",
            "name": "Hinokami Kagura",
            "type": "attack"
        },
        
        # JoJo skills
        {
            "anime": "jojo",
            "name": "Star Platinum",
            "type": "attack"
        },
        {
            "anime": "jojo",
            "name": "Time Stop",
            "type": "buff"
        }
    ]
    
    print(f"\n🚀 Generating {len(skills)} skills in batch...")
    generator.batch_generate(skills)
    print("\n✅ Batch generation complete!")


def example_custom_parameters():
    """Example 4: Custom parameters"""
    print("\n" + "="*60)
    print("EXAMPLE 4: Custom Skill with Specific Parameters")
    print("="*60)
    
    # You can create a skill with very specific parameters
    custom_skill = """# Custom Skill: Final Flash (Vegeta - Dragon Ball)
# Type: Charge attack
# Mana: 90 | Cooldown: 25s | Damage: 20 hearts

command /finalflash:
    permission: animeskills.dragon-ball.final-flash
    cooldown: 25 seconds
    cooldown message: §c[AnimeSkills] Aguarde %remaining time%!
    trigger:
        if {player_mana_%player%} >= 90:
            # Charging phase (must hold shift)
            send "§e[Final Flash] §fIniciando carregamento..." to player
            send "§7Segure SHIFT por 3 segundos!" to player
            
            set {_charging_%player%} to true
            wait 3 seconds
            
            if player is sneaking:
                # Full power release
                send "§e§l[Final Flash] §f§lFINAL FLASH!" to player
                
                # Massive beam
                loop 30 times:
                    set {_loc} to location loop-number meters in front of player
                    play "EXPLOSION_LARGE" at {_loc}
                    play "FIREWORKS_SPARK" at {_loc}
                    
                    loop all entities in radius 3 of {_loc}:
                        if loop-entity-2 is not player:
                            damage loop-entity-2 by 20 hearts
                    
                    wait 1 tick
                
                subtract 90 from {player_mana_%player%}
                add_skill_xp(player, 40)
            else:
                send "§c[Final Flash] §fCarregamento cancelado!" to player
        else:
            send "§c[AnimeSkills] Energia insuficiente! (90 necessário)" to player
"""
    
    print("\n📝 Custom Skill Template:")
    print(custom_skill)
    
    print("\n💡 This shows how to add custom mechanics:")
    print("  - Charging mechanics (hold shift)")
    print("  - Conditional execution")
    print("  - Custom particle patterns")
    print("  - High-power ultimate skills")


def example_copilot_workflow():
    """Example 5: GitHub Copilot workflow"""
    print("\n" + "="*60)
    print("EXAMPLE 5: GitHub Copilot Workflow")
    print("="*60)
    
    workflow = """
    STEP-BY-STEP COPILOT WORKFLOW:
    
    1. 📝 Open VS Code with Copilot extension
    
    2. 🎯 Create new file: my_custom_skill.sk
    
    3. 💭 Write descriptive comments:
       
       # Create a Hunter x Hunter skill: Nen Ability
       # The user can choose between enhancement, emission, or manipulation
       # Each type has different effects and costs
       # Mana cost varies: 40 (enhancement), 60 (emission), 80 (manipulation)
       # Cooldown: 15 seconds for all types
       
    4. ⌨️ Start typing and let Copilot suggest:
       
       command /nen:
           # Copilot will now suggest the full implementation!
           
    5. ✨ Refine the suggestions:
       - Use Tab to accept
       - Use Alt+] to see alternatives
       - Edit as needed
       
    6. 🧪 Test in Minecraft:
       /skript reload my_custom_skill.sk
       
    7. 🔄 Iterate and improve
    """
    
    print(workflow)
    
    print("\n💡 Pro Tips for Copilot:")
    print("  • Be very specific in comments")
    print("  • Reference existing skills for context")
    print("  • Describe visual effects in detail")
    print("  • Mention anime mechanics explicitly")


def example_skill_variations():
    """Example 6: Creating skill variations"""
    print("\n" + "="*60)
    print("EXAMPLE 6: Skill Variations (Powered-up versions)")
    print("="*60)
    
    variations = {
        "Basic Rasengan": {
            "mana": 40,
            "cooldown": 8,
            "damage": 12
        },
        "Giant Rasengan": {
            "mana": 60,
            "cooldown": 12,
            "damage": 18,
            "radius": 5
        },
        "Ultra-Big Ball Rasengan": {
            "mana": 100,
            "cooldown": 20,
            "damage": 25,
            "radius": 8
        }
    }
    
    print("\n🎯 Skill Progression System:")
    for name, stats in variations.items():
        print(f"\n{name}:")
        for key, value in stats.items():
            print(f"  {key}: {value}")
    
    print("\n💡 You can create progression tiers:")
    print("  • Basic → Advanced → Ultimate")
    print("  • Unlock higher tiers at certain levels")
    print("  • Each tier increases cost and power")


def main():
    """Run all examples"""
    print("\n" + "="*70)
    print("🎮 MINECRAFT ANIME SKILLS - USAGE EXAMPLES")
    print("="*70)
    
    # Run examples
    example_basic_generation()
    example_natural_language()
    example_batch_generation()
    example_custom_parameters()
    example_copilot_workflow()
    example_skill_variations()
    
    print("\n" + "="*70)
    print("✅ ALL EXAMPLES COMPLETED!")
    print("="*70)
    print("\n💡 Next Steps:")
    print("  1. Try generating your own skills")
    print("  2. Experiment with different anime sources")
    print("  3. Use GitHub Copilot for advanced features")
    print("  4. Share your creations with the community!")
    print("\n🎯 Happy Coding! ⚡")


if __name__ == "__main__":
    main()
