import java.io.Serializable;


/**
 * Êëàññ îïèñûâàåò ÷àñòü òåëà, îò íåãî
 * óíàñëåäîâàííû âñå îñòàëüíûå ÷àñòè 
 * 
 * @author Àðò¸ì Ïîïîâ
 *
 * @version 1.0
 */

public abstract class BodyPart extends Sprite implements Serializable {
	public static final String BODY_PART_IMAGE_PATH = "/res/parts/";
	
	//Èíôîðìàöèÿ î òêàíÿõ
	
	public static final int MEAT_COST = 5; // çàòðàòû êàæäîé êëåòêîé åäèíèö ýíåðãèè
	public static final int FAT_COST = -2; // æèðû ïîçâîëÿþò íàîáîðîò çàïàñàòüñÿ ýíåðãèåé
	public static final int SKIN_COST = 1; 
	
	public static final int SKIN_WEIGHT = 2; //âåñ êëåòîê êîæè
	public static final int FAT_WEIGHT = 1;
	public static final int MEAT_WEIGHT = 3;
	
	public static final int SKIN_COLD_RESIST = 1;
	public static final int FAT_COLD_RESIST = 2;
	public static final int FAT_HOT_RESIST = -1;
	public static final int SKIN_HOT_RESIST = 2;
	
	public static final int MEAT_MATERIAL = 0xff0000; // êðàñíûé öâåò ïèêñåëåé ñîîòâåòñòâóåò òêàíÿì ìûøö
	public static final int SKIN_MATERIAL = 0x939393; // öâåò ïèêñåëåé äëÿ êîæè
	public static final int FAT_MATERIAL = 0x334455; // öâåò ïèêñåëåé äëÿ æèðà
	
	// Êîíåö èíôîðìàöèè î òêàíÿõ
	
	
	private int relX, relY; //ñìåùåíèå îòíîñèòåëüíî öåíòðà ìàññ òåëà
    private int orientation; //îðèåíòàöèÿ êîíå÷íîñòè(ïîäðîáíåå ñì â bodyPartINFO
    private int weight; //âëèÿåò íà ñêîðîñòü.
    private int defence; // óðîâåíü çàùèòû îò âíåøíèõ âîçäåéòñòâèé(÷åì áîëüøå êëåòîê êîæè, òåì âûøå)
    private int energyCosts; // çàòðàòû ýòîé ÷àñòüþ ýíåðãèè
    private PartFunction[] avalibleFunctions; // ôóíêöèè, êîòîðûå ñïîñîáíà âûïîëíÿòü ýòà ÷àñòü òåëà
    private boolean isActive; // àêòèâíà ëè â äàííûé ìîìåíò ÷àñòü
    private Creature parent;
    
    /*other properties
     * can be supplemented later
     */
    
    private int fatCells; //êîëè÷åñòâî êëåòîê æèðà
    private int meatCells;
    private int skinCells;
    private int hotResist;  //1-100 
    private int coldResist; //1-100
    
    //end of other properties
    
    BodyPart(int relX, int relY, int width, int height, int orientation
    		,int[] pixels) {
    	
    	super(width, height, pixels);
    	
    	this.relX = relX;
    	this.relY = relY;
    	this.orientation = orientation;
    	
    	calculateProperties();
    }
    
    /** èñïîëüçóåòñÿ äëÿ ðàñ÷åòà çàòðàò ýíåðãèè,
     *  âåñà è äðóãèõ õàðàêòåðèñòèê ÷àñòè òåëà
     * ÷àñòè òåëà
     */
    private void calculateProperties() {
    	//ïîäñ÷åò êëåòîê ðàçíîãî òèïà
    	
    	for(int i = 0; i < pixels.length; i++) {
    		switch(pixels[i]) {
    		case MEAT_MATERIAL:
    			meatCells++;
    			break;
    		case FAT_MATERIAL:
    			fatCells++;
    			break;
    		case SKIN_MATERIAL:
    			skinCells++;
    			break;
    		}
    	}
    	
    	energyCosts = (fatCells * FAT_COST) + (skinCells * SKIN_COST) + (meatCells * MEAT_COST);
    	weight = (fatCells * FAT_WEIGHT) + (skinCells * SKIN_WEIGHT) + (meatCells * MEAT_WEIGHT);
    	
    	coldResist = fatCells * FAT_COLD_RESIST + skinCells * SKIN_COLD_RESIST;
    	hotResist = fatCells * FAT_HOT_RESIST + skinCells * SKIN_HOT_RESIST;
    	
    	unlockFunctions();
    }
    
    /**
     * Îïðåäåëÿåò, êàêèå ôóíêöèè èç íàáîðà áóäóò
     * äîñòóïíû äëÿ äàííîé ÷àñòè òåëà
     */
    protected abstract void unlockFunctions();
    
    public int getOrientation() {
    	return orientation;
    }
    
    public PartFunction[] getFunctions() {
    	return this.avalibleFunctions;
    }
    
    public int getWeight() {
    	return weight;
    }
    
    public int getDefence() {
    	return defence;
    }
    
    public Creature getParent() {
    	return parent;
    }
    
    /*îïèñàíèèå ôóíêöèé ÷àñòè òåëà
     *ñ ïîìîùüþ ýòîãî êëàññà áóäåò
     *îïðåäåëÿòñÿ, äîñòóïíà ëè òà èëè 
     *èíàÿ ôóíêöèÿ
    */
    public static class PartFunction{
    	private int number;
    	private String name;
    	
    	private int fatRequirement;
    	private int skinRequirement;
    	private int meatRequirement;
    	
    	private boolean isAvalible = false;//Ошибка в слове: available
    	
    	PartFunction(int number, String name, int fatRequirement, int skinRequirement, int meatRequirement) {
    		this.number = number;
    		this.name = name;
    		this.fatRequirement = fatRequirement;
    		this.skinRequirement = skinRequirement;
    		this.meatRequirement = meatRequirement;
    	}
    	
    	public int getNumber() {
    		return number;
    	}
    	
    	public String getName() {
    		return name;
    	}
    	
    	public int getMeatRequirement() {
    		return this.meatRequirement;
    	}
    	
    	public int getFatRequirement() {
    		return this.fatRequirement;
    	}
    	
    	public int getSkinRequirement() {
    		return this.skinRequirement;
    	}
    	
    	

    	public boolean isAvalible() {
    		return isAvalible;
    	}
    	
    	public void setAvalible(boolean av) {
    		this.isAvalible = av;
    	}
    	
    }

	public void setFunctions(PartFunction[] func) {
		this.avalibleFunctions = func;
	}
	
	public int getFatCells() {
		return this.fatCells;
	}
	
	public int getSkinCells() {
		return skinCells;
	}
	
	public int getMeatCells() {
		return meatCells;
	}
    
    
}
