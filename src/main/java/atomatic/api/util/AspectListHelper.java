package atomatic.api.util;

import thaumcraft.api.aspects.AspectList;

public class AspectListHelper
{
    public static boolean equals(AspectList list1, AspectList list2)
    {
        if (list1.size() != list2.size())
        {
            return false;
        }

        if (list1.visSize() != list2.visSize())
        {
            return false;
        }

        if (list1.getAspectsSorted().length != list2.getAspectsSorted().length)
        {
            return false;
        }

        for (int i = 0; i < list1.getAspectsSorted().length; i++)
        {
            if (list1.getAspectsSorted()[i].getTag().equals(list2.getAspectsSorted()[i].getTag()))
            {
                return false;
            }

            if (list1.getAmount(list1.getAspectsSorted()[i]) != list2.getAmount(list2.getAspectsSorted()[i]))
            {
                return false;
            }
        }

        return true;
    }

    public static String toString(AspectList list)
    {
        String s = "AspectList{";

        for (int i = 0; i < list.size(); i++)
        {
            if (i == 0)
            {
                s = s + list.getAspectsSorted()[i].getTag() + "=" + list.getAmount(list.getAspectsSorted()[i]);
            }
            else
            {
                s = ", " + s + list.getAspectsSorted()[i].getTag() + "=" + list.getAmount(list.getAspectsSorted()[i]);
            }
        }

        return s + "}";
    }
}
