import java.io.Serializable;


/**
 * Класс описывает часть тела, от него
 * унаследованны все остальные части 
 * 
 * @author Артём Попов
 *
 * @version 1.0
 */

public abstract class BodyPart extends Sprite implements Serializable {
	public static final String BODY_PART_IMAGE_PATH = "/res/parts/";
	
	//Информация о тканях
	
	public static final int MEAT_COST = 5; // затраты каждой клеткой единиц энергии
	public static final int FAT_COST = -2; // жиры позволяют наоборот запасаться энергией
	public static final int SKIN_COST = 1; 
	
	public static final int SKIN_WEIGHT = 2; //вес клеток кожи
	public static final int FAT_WEIGHT = 1;
	public static final int MEAT_WEIGHT = 3;
	
	public static final int SKIN_COLD_RESIST = 1;
	public static final int FAT_COLD_RESIST = 2;
	public static final int FAT_HOT_RESIST = -1;
	public static final int SKIN_HOT_RESIST = 2;
	
	public static final int MEAT_MATERIAL = 0xff0000; // красный цвет пикселей соответствует тканям мышц
	public static final int SKIN_MATERIAL = 0x939393; // цвет пикселей для кожи
	public static final int FAT_MATERIAL = 0x334455; // цвет пикселей для жира
	
	// Конец информации о тканях
	
	
	private int relX, relY; //смещение относительно центра масс тела
    private int orientation; //ориентация конечности(подробнее см в bodyPartINFO
    private int weight; //влияет на скорость.
    private int defence; // уровень защиты от внешних воздейтствий(чем больше клеток кожи, тем выше)
    private int energyCosts; // затраты этой частью энергии
    private PartFunction[] avalibleFunctions; // функции, которые способна выполнять эта часть тела
    private boolean isActive; // активна ли в данный момент часть
    private Creature parent;
    
    /*other properties
     * can be supplemented later
     */
    
    private int fatCells; //количество клеток жира
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
    
    /** используется для расчета затрат энергии,
     *  веса и других характеристик части тела
     * части тела
     */
    private void calculateProperties() {
    	//подсчет клеток разного типа
    	
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
     * Определяет, какие функции из набора будут
     * доступны для данной части тела
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
    
    /*описаниие функций части тела
     *с помощью этого класса будет
     *определятся, доступна ли та или 
     *иная функция
    */
    public static class PartFunction{
    	private int number;
    	private String name;
    	
    	private int fatRequirement;
    	private int skinRequirement;
    	private int meatRequirement;
    	
    	private boolean isAvalible = false;
    	
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
