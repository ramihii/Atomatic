package atomatic.research;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.research.ResearchPage;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ResearchContainer
{
    public final String key;
    public String[] parents = null;
    public String[] parentsHidden = null;
    public String[] siblings = null;
    public final int displayColumn;
    public final int displayRow;
    public final ItemStack icon_item;
    public final ResourceLocation icon_resource;
    private int complexity;
    private boolean isSpecial;
    private boolean isSecondary;
    private boolean isRound;
    private boolean isStub;
    private boolean isVirtual;
    private boolean isConcealed;
    private boolean isHidden;
    private boolean isAutoUnlock;
    private ItemStack[] itemTriggers;
    private String[] entityTriggers;
    private Aspect[] aspectTriggers;
    private ResearchPage[] pages = null;
    private int warp;

    public ResearchContainer(String key, int col, int row, int complex, ResourceLocation icon)
    {
        this.key = key;
        this.icon_resource = icon;
        this.icon_item = null;
        this.displayColumn = col;
        this.displayRow = row;
        this.complexity = complex;
        if (complexity < 1)
        {
            this.complexity = 1;
        }
        if (complexity > 3)
        {
            this.complexity = 3;
        }
    }

    public ResearchContainer(String key, int col, int row, int complex, ItemStack icon)
    {
        this.key = key;
        this.icon_item = icon;
        this.icon_resource = null;
        this.displayColumn = col;
        this.displayRow = row;
        this.complexity = complex;
        if (complexity < 1)
        {
            this.complexity = 1;
        }
        if (complexity > 3)
        {
            this.complexity = 3;
        }
    }

    public ResearchContainer setSpecial()
    {
        this.isSpecial = true;
        return this;
    }

    public ResearchContainer setStub()
    {
        this.isStub = true;
        return this;
    }

    public ResearchContainer setConcealed()
    {
        this.isConcealed = true;
        return this;
    }

    public ResearchContainer setHidden()
    {
        this.isHidden = true;
        return this;
    }

    public ResearchContainer setVirtual()
    {
        this.isVirtual = true;
        return this;
    }

    public ResearchContainer setParents(String... par)
    {
        this.parents = par;
        return this;
    }


    public ResearchContainer setParentsHidden(String... par)
    {
        this.parentsHidden = par;
        return this;
    }

    public ResearchContainer setSiblings(String... sib)
    {
        this.siblings = sib;
        return this;
    }

    public ResearchContainer setPages(ResearchPage... par)
    {
        this.pages = par;
        return this;
    }

    public ResearchPage[] getPages()
    {
        return pages;
    }

    public ResearchContainer setItemTriggers(ItemStack... par)
    {
        this.itemTriggers = par;
        return this;
    }

    public ResearchContainer setEntityTriggers(String... par)
    {
        this.entityTriggers = par;
        return this;
    }

    public ResearchContainer setAspectTriggers(Aspect... par)
    {
        this.aspectTriggers = par;
        return this;
    }

    public ItemStack[] getItemTriggers()
    {
        return itemTriggers;
    }

    public String[] getEntityTriggers()
    {
        return entityTriggers;
    }

    public Aspect[] getAspectTriggers()
    {
        return aspectTriggers;
    }

    public boolean isSpecial()
    {
        return this.isSpecial;
    }

    public boolean isStub()
    {
        return this.isStub;
    }

    public boolean isConcealed()
    {
        return this.isConcealed;
    }

    public boolean isHidden()
    {
        return this.isHidden;
    }

    public boolean isVirtual()
    {
        return this.isVirtual;
    }

    public boolean isAutoUnlock()
    {
        return isAutoUnlock;
    }

    public ResearchContainer setAutoUnlock()
    {
        this.isAutoUnlock = true;
        return this;
    }

    public boolean isRound()
    {
        return isRound;
    }

    public ResearchContainer setRound()
    {
        this.isRound = true;
        return this;
    }

    public boolean isSecondary()
    {
        return isSecondary;
    }

    public ResearchContainer setSecondary()
    {
        this.isSecondary = true;
        return this;
    }

    public int getComplexity()
    {
        return complexity;
    }

    public ResearchContainer setComplexity(int complexity)
    {
        this.complexity = complexity;
        return this;
    }

    public boolean hasWarp()
    {
        return warp > 0;
    }

    public int getWarp()
    {
        return warp;
    }

    public ResearchContainer setWarp(int warp)
    {
        this.warp = warp;
        return this;
    }
}
