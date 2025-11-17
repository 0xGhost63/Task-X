import java.util.*;

//Comparators + Hashmaps !
class RecordUtil
{
        Comparator <Record> classComparator = new Comparator<Record>() 
        {
            @Override
            public int compare (Record a , Record b)
            {
                if(a instanceof Assignment && b instanceof Quiz)
                {
                    return 1;
                }
                else if(a instanceof Quiz && b instanceof Assignment)
                {
                    return -1;
                }
                else 
                {
                    return 0;
                }
            }
        };

        Comparator <Record> deadlineComparator = (Record a1,Record a2)
        -> a1.getDeadline().compareTo(a2.getDeadline());

        // HashMap

        public static final Map <String,Integer> coursePriority = new HashMap<>();
        static
            {
                coursePriority.put("OOP",1);
                coursePriority.put("DS",2);
                coursePriority.put("Expo",3);
                coursePriority.put("PP",4);
                coursePriority.put("CCE",5);
                coursePriority.put("ICP",6);
                coursePriority.put("Pre-Cal",7);

            }

}