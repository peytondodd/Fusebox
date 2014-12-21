package net.lightcraftmc.fusebox.util.extra.extraeffects;

import net.lightcraftmc.fusebox.util.effects.EffectManager;
import net.lightcraftmc.fusebox.util.extra.ExtraManager;
import net.lightcraftmc.fusebox.util.particles18.ParticleLib18;
import net.lightcraftmc.fusebox.util.update.UpdateType;
import net.lightcraftmc.fusebox.util.update.event.UpdateEvent;
import net.lightcraftmc.fusebox.util.MathUtils;
import net.lightcraftmc.fusebox.util.UtilVector;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

public class Vortex
  implements Listener
{
  private int particles = 130;
  private int particlesPerIteration = 8;
  private float size = 1.0F;
  private float xFactor = 1.0F;
  private float yFactor = 0.6F;
  private float zFactor = 1.0F;
  private float yOffset = 0.6F;
  private double xRotation;
  private double yRotation;
  private double zRotation = 0.0D;
  private int step;

  @EventHandler
  public void LocationUpdater(UpdateEvent event)
  {
    if (event.getType() == UpdateType.TICK)
    {
      for (Player p : EffectManager.effect3.keySet())
      {
        if (EffectManager.getEffect(p) == EffectManager.EffectType.Vortex)
        {
          if (p.isValid())
          {
            if (ExtraManager.isMoving(p))
            {
              Location location = p.getLocation();
              Vector vector = new Vector();

              for (int i = 0; i < this.particlesPerIteration; i++) {
                this.step += 1;

                float t = 3.141593F / this.particles * this.step;
                float r = MathUtils.sin(t * 2.718282F * this.particlesPerIteration / this.particles) * this.size;
                float s = r * 3.141593F * t;

                vector.setX(this.xFactor * r * -MathUtils.cos(s));
                vector.setZ(this.zFactor * r * -MathUtils.sin(s));
                vector.setY(this.yFactor * MathUtils.cos(r / 3.141593F * s) + this.yOffset);

                UtilVector.rotateVector(vector, this.xRotation, this.yRotation, this.zRotation);
                
     		   	ParticleLib18 flame = new ParticleLib18(net.lightcraftmc.fusebox.util.particles18.ParticleLib18.ParticleType.FLAME, 0.0D, 1, 0.0D);
     		   	flame.sendToLocation(location.add(vector));

                location.subtract(vector);
              }

            }
            else if (!p.isInsideVehicle()) {
   		   	  ParticleLib18 flame = new ParticleLib18(net.lightcraftmc.fusebox.util.particles18.ParticleLib18.ParticleType.DRIP_WATER, 0.1000000014901161D, 4, 0.300000011920929D);
   		   	  flame.sendToLocation(p.getLocation().add(0.0D, 1.0D, 0.0D));

            }
          }
        }
      }
    }
  }
}