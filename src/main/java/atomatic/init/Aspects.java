package atomatic.init;

import atomatic.aspects.AspectStack;
import atomatic.reference.Researches;
import atomatic.util.LogHelper;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

import com.google.common.collect.Maps;

import java.util.HashMap;

public class Aspects
{
    public static final String DEFAULT_KEY = "NIL";

    public static HashMap<String, HashMap<String, AspectList>> arcaneAspects = Maps.newHashMap();
    public static HashMap<String, AspectList> researchAspects = Maps.newHashMap();

    public static void init()
    {
        initArcaneAspects();
        initResearchAspects();
    }

    static void initArcaneAspects()
    {
    }

    static void initResearchAspects()
    {
        addResearchAspects(Researches.REVERSED_DIRT, new AspectStack(Aspect.VOID), new AspectStack(Aspect.ENERGY), new AspectStack(Aspect.EARTH));
        addResearchAspects(Researches.ATOM, AspectStack.createStacks(Aspect.AIR, Aspect.EARTH, Aspect.FIRE, Aspect.WATER, Aspect.ORDER, Aspect.ENTROPY));
    }

    static void addArcaneAspects(String research, AspectStack... aspects)
    {
        addArcaneAspects(research, DEFAULT_KEY, aspects);
    }

    static void addArcaneAspects(String research, String key, AspectStack... aspects)
    {
        HashMap<String, AspectList> map;

        if (arcaneAspects.containsKey(research))
        {
            map = arcaneAspects.get(research);
        }
        else
        {
            map = Maps.newHashMap();
        }

        if (!map.containsKey(key))
        {
            AspectList aspectList = new AspectList();

            for (AspectStack stack : aspects)
            {
                aspectList.add(stack.getAspect(), stack.getAmount());
            }

            map.put(key, aspectList);

            arcaneAspects.put(research, map);
        }
        else
        {
            LogHelper.warn("Trying to register duplicate arcane aspects (research:" + research + ",key:" + key + ")");
        }
    }

    static AspectList getArcaneAspects(String research)
    {
        return getArcaneAspects(research, DEFAULT_KEY);
    }

    static AspectList getArcaneAspects(String research, String key)
    {
        if (arcaneAspects.containsKey(research))
        {
            if (arcaneAspects.get(research).containsKey(key))
            {
                return arcaneAspects.get(research).get(key);
            }
        }

        return new AspectList(); // TODO Is null better?
    }

    static void addResearchAspects(String research, AspectStack... aspects)
    {
        if (!researchAspects.containsKey(research))
        {
            AspectList aspectList = new AspectList();

            for (AspectStack stack : aspects)
            {
                aspectList.add(stack.getAspect(), stack.getAmount());
            }

            researchAspects.put(research, aspectList);
        }
        else
        {
            LogHelper.warn("Trying to register duplicate research aspects (research:" + research + ")");
        }
    }

    static AspectList getResearchAspects(String research)
    {
        if (researchAspects.containsKey(research))
        {
            return researchAspects.get(research);
        }

        return new AspectList(); // TODO Is null better?
    }
}
