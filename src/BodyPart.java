import java.io.Serializable;


public abstract class BodyPart extends Sprite implements Serializable {
	public static final int MEAT_COST = 5; // затраты каждой клеткой единиц энергии
	public static final int FAT_COST = -2; // жиры позвол€ют наоборот запасатьс€ энергией
	public static final int SKIN_COST = 1; 
	
	public static final int MEAT_MATERIAL = 0xff0000; // красный цвет пикселей соответствует ткан€м мышц
	public static final int SKIN_MATERIAL = 0x939393; // цвет пикселей дл€ кожи
	public static final int FAT_MATERIAL = 0x334455; // цвет пикселей дл€ жира
	
	private int relX, relY; //смещение относительно центра масс тела
    private int orientation; //ориентаци€ конечности(подробнее см в bodyPartINFO
    private int energyCosts; // затраты этой частью энергии
    private PartFunction[] avalibleFunctions; // функции, которые способна выполн€ть эта часть тела
    private boolean isActive; // активна ли в данный момент часть
    
    /*other properties
     * can be supplemented later
     */
    
    private int fatCells; //количество клеток жира
    private int meatCells;
    private int skinCells;
    private int hotResist;  //1-100 
    private int coldResist; //1-100
    
    //end of other properties
    
    BodyPart(int relX, int relY, int width, int height, int orientation,
    		int hotResist, int coldResist, PartFunction[] avalibleFunctions, 
    		int[] pixels) {
    	
    	super(width, height);
    	
    	this.relX = relX;
    	this.relY = relY;
    	this.orientation = orientation;
    	this.avalibleFunctions = avalibleFunctions;
    	
    	this.hotResist = hotResist;
    	this.coldResist = coldResist;
    	
    	this.pixels = pixels; //внешний вид части тела
    	
    	calculateProperties();
    }
    
    /** используетс€ дл€ расчета затрат энергии,
     *  и других характеристик части тела
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
    	
    	unlockFunctions();
    }
    
    /**
     * ќпредел€ет, какие функции из набора будут
     * доступны дл€ данной части тела
     */
    protected abstract void unlockFunctions();
    
    /*описаниие функций части тела
     *с помощью этого класса будет
     *определ€тс€, доступна ли та или 
     *ина€ функци€
    */
    public static class PartFunction{
    	private int number;
    	
    	private int fatRequirement;
    	private int skinRequirement;
    	private int meatRequirements;
    	
    	PartFunction(int number, int fatRequirement, int skinRequirement, int meatRequirement) {
    		this.number = number;
    		this.fatRequirement = fatRequirement;
    		this.skinRequirement = skinRequirement;
    		this.meatRequirements = meatRequirement;
    	}
    	
    }
}
