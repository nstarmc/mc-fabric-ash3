package de.miraisoft.ash2;

public enum DirectionEnum {

	N("North"), NE("North-east"), E("East"), SE("South-east"), S("South"), SW("South-west"), W("West"),
	NW("North-west");

	public String longName;

	private DirectionEnum(String longName) {
		this.longName = longName;
	}

	public static DirectionEnum getByYawDegrees(float degrees) {
		degrees += 180;
		switch (Math.round(degrees / 45)) {
		case 0:
			return N;
		case 1:
			return NE;
		case 2:
			return E;
		case 3:
			return SE;
		case 4:
			return S;
		case 5:
			return SW;
		case 6:
			return W;
		case 7:
			return NW;
		// case 8: return N;
		default:
			return N;

		}
	}
}
