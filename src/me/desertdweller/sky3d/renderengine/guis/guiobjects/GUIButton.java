package me.desertdweller.sky3d.renderengine.guis.guiobjects;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class GUIButton extends GUIObject{

	private ButtonType type;
	private ButtonState state;
	private int normalTexture;
	private int hoveredTexture = -1;
	private int pressedTexture = -1;
	private int disabledTexture = -1;
	private boolean disabled = false;
	private boolean toggled = false;
	private boolean beenClicked = false;
	
	
	public GUIButton(int texture, Vector2f position, Vector2f scale, float opacity, float cornerRadius, Vector3f colour, ButtonType type, ButtonState state) {
		super(texture, position, scale, opacity, cornerRadius, colour);
		normalTexture = texture;
		this.type = type;
		this.state = state;
	}
	
	public GUIButton(int texture, Vector2f position, Vector2f scale, float opacity, float cornerRadius, Vector3f colour, GUITransition transition, ButtonType type, ButtonState state) {
		super(texture, position, scale, opacity, cornerRadius, colour, transition);
		normalTexture = texture;
		this.type = type;
		this.state = state;
	}
	
	public GUIButton(GUIObject parent, int texture, Vector2f position, Vector2f scale, float opacity, float cornerRadius, Vector3f colour, ButtonType type, ButtonState state) {
		super(parent, texture, position, scale, opacity, cornerRadius, colour);
		normalTexture = texture;
		this.type = type;
		this.state = state;
	}
	
	public GUIButton(GUIObject parent, int texture, Vector2f position, Vector2f scale, float opacity, float cornerRadius, Vector3f colour, GUITransition transition, ButtonType type, ButtonState state) {
		super(parent, texture, position, scale, opacity, cornerRadius, colour, transition);
		normalTexture = texture;
		this.type = type;
		this.state = state;
	}
	
	@Override
	protected void onMouseHover() {
		if(disabled)
			return;
		if(type == ButtonType.TOGGLABLE && toggled) {
			return;
		}
		state = ButtonState.HOVERED;
		if(hoveredTexture != -1) 
			this.texture = hoveredTexture;
	}
	
	@Override
	protected void onMouseUnHover() {
		if(disabled)
			return;
		if(type == ButtonType.TOGGLABLE && toggled) {
			return;
		}
		state = ButtonState.NORMAL;
		this.texture = normalTexture;
	}
	
	@Override
	protected void onMouseClicked() {
		if(disabled)
			return;
		state = ButtonState.PRESSED;
		if(pressedTexture != -1)
			this.texture = pressedTexture;
	}
	
	@Override
	protected void onMouseBadRelease() {
		if(disabled)
			return;
		if(type == ButtonType.TOGGLABLE && toggled) {
			state = ButtonState.PRESSED;
			if(pressedTexture != -1)
				this.texture = pressedTexture;
		}else{
			state = ButtonState.NORMAL;
			this.texture = normalTexture;
		}
	}
	
	@Override
	protected void onMouseGoodRelease() {
		if(disabled)
			return;
		if(type != ButtonType.TOGGLABLE || (type == ButtonType.TOGGLABLE && toggled)) {
			state = ButtonState.HOVERED;
			if(hoveredTexture != -1)
				this.texture = hoveredTexture;
			toggled = false;
			beenClicked = true;
		}else if(type == ButtonType.TOGGLABLE && !toggled) {
			if(pressedTexture != -1)
				this.texture = pressedTexture;
			state = ButtonState.PRESSED;
			toggled = true;
			beenClicked = true;
		}
		
	}
	
	public void setDisabled(boolean disabled) {
		if(disabled) {
			state = ButtonState.DISABLED;
			if(disabledTexture != -1)
				this.texture = disabledTexture;
		}else {
			state = ButtonState.NORMAL;
			this.texture = normalTexture;
		}
		this.disabled = disabled;
	}
	
	public boolean hasBeenClicked() {
		boolean bool = beenClicked;
		beenClicked = false;
		return bool;
	}
	
	public boolean isDisabled() {
		return disabled;
	}

	public ButtonType getType() {
		return type;
	}

	public void setType(ButtonType type) {
		this.type = type;
	}

	public ButtonState getState() {
		return state;
	}

	public void setState(ButtonState state) {
		this.state = state;
	}

	public int getNormalTexture() {
		return normalTexture;
	}

	public void setNormalTexture(int normalTexture) {
		this.normalTexture = normalTexture;
	}

	public int getHoveredTexture() {
		return hoveredTexture;
	}

	public void setHoveredTexture(int hoveredTexture) {
		this.hoveredTexture = hoveredTexture;
	}

	public int getPressedTexture() {
		return pressedTexture;
	}

	public void setPressedTexture(int pressedTexture) {
		this.pressedTexture = pressedTexture;
	}

	public int getDisabledTexture() {
		return disabledTexture;
	}

	public void setDisabledTexture(int disabledTexture) {
		this.disabledTexture = disabledTexture;
	}

	public boolean isToggled() {
		return toggled;
	}

	public void setToggled(boolean toggled) {
		this.toggled = toggled;
	}
}
