package casses.dragon;

public enum Eatable {
	
	Imposible(10000000),
	Hard(100000),
	Difficult(10),
	Mediocre(10),
	Medium(10),
	Easy(10),
	Crappy(0);
	
	private static Eatable def = Crappy;
	private float level;
	
	Eatable(float level) {
		this.level = level;
	}
	
	public static Eatable getLevel(float amount) {
		for(Eatable eatable : Eatable.values()) {
			if(eatable.level <= amount) return eatable;
		}
		return def;
	}

}
