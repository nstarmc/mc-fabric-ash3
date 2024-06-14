package cn.nstarmc.ash3;

import net.minecraft.client.resource.language.I18n;

public enum DirectionEnum {

	N(I18n.translate("direction.north")),
	NE(I18n.translate("direction.northeast")),
	E(I18n.translate("direction.east")),
	SE(I18n.translate("direction.southeast")),
	S(I18n.translate("direction.south")),
	SW(I18n.translate("direction.southwest")),
	W(I18n.translate("direction.west")),
	NW(I18n.translate("direction.northwest"));


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
	
	public String getX() {
		if (DirectionEnum.E.equals(this)) {
			return " ++";
		} else if (DirectionEnum.NE.equals(this)) {
			return " +";
		} else if (DirectionEnum.SE.equals(this)) {
			return " +";
		} else if (DirectionEnum.W.equals(this)) {
			return " --";
		} else if (DirectionEnum.NW.equals(this)) {
			return " -";
		} else if (DirectionEnum.SW.equals(this)) {
			return " -";
		} else {
			return "";
		}
	}

	public String getZ() {
		if (DirectionEnum.S.equals(this)) {
			return " ++";
		} else if (DirectionEnum.SE.equals(this)) {
			return " +";
		} else if (DirectionEnum.SW.equals(this)) {
			return " +";
		} else if (DirectionEnum.N.equals(this)) {
			return " --";
		} else if (DirectionEnum.NE.equals(this)) {
			return " -";
		} else if (DirectionEnum.NW.equals(this)) {
			return " -";
		} else {
			return "";
		}
	}
}
