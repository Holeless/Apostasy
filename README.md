# Grenade (Fabric, Minecraft 1.21.1)

Hold the grenade out, pull the pin, throw it, and it explodes where it hits.

- **Right-click the grenade** – pulls the pin (it becomes the armed, pinless grenade and the pin pops out).
- **Right-click the armed grenade** – throws it. It explodes on impact (power 3.0; TNT is 4.0).
- **Craft:** iron nugget (top), iron ingots (left/right/bottom), gunpowder (centre).
- Both items are also in the Combat creative tab.

## Build

    ./gradlew build

The mod jar ends up in `build/libs/grenade-<version>.jar` (ignore the `-sources` jar). Needs Fabric Loader and Fabric API.

## Tuning

- **How the grenade sits in the hand** (first/third person, ground, GUI): the `display` blocks in
  `src/main/resources/assets/grenade/models/item/*.json`. Open them in Blockbench, use the Display tab, export.
- **Arm pose / throw swing:** `src/client/java/com/grenade/client/mixin/HumanoidModelMixin.java`.
- **Explosion size:** `EXPLOSION_POWER` in `GrenadeProjectile.java`.
- **Throw speed:** the `1.4F` in `ArmedGrenadeItem.java`.
