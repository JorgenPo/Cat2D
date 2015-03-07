
public class CenterPart extends BodyPart {
	
	private int foodType; //тип питания
	private int sexType; //один или с партнером
	
	CenterPart(int width, int height,int[] pixels) {
		super(0, 0, width, height, 0, pixels);
		
		PartFunction[] func = new PartFunction[4];
		
		func[0] = new PartFunction(1, "SunFeed", 1, 2, 0);
		func[1] = new PartFunction(2, "WeedFeed", 2, 2, 1);
		func[2] = new PartFunction(3, "SelfSex", 1, 1, 1);
		func[3] = new PartFunction(4, "PartnerSex", 3, 3, 3);
		
		super.setFunctions(func);
	}
	
	protected void unlockFunctions() {
		for(int i = 0; i < super.getFunctions().length; i++ ) {
			PartFunction func = super.getFunctions()[i];
			func.setAvalible((super.getMeatCells() >= func.getMeatRequirement())
					&& (super.getFatCells() >= func.getFatRequirement())
					&& (super.getSkinCells() >= func.getSkinRequirement()));
		}

	}
	
	public void addPart(BodyPart part) {
		part.getParent().add(part);
	}

}
