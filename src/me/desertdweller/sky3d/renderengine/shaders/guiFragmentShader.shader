#version 140

in vec2 textureCoords;

out vec4 out_Color;

uniform sampler2D guiTexture;
uniform vec3 modColour;
uniform float opacity;
uniform float cornerRadius;

void main(void){

	vec2 topLeft = vec2(cornerRadius, cornerRadius);
	vec2 topRight = vec2(1.0 - cornerRadius, cornerRadius);
	vec2 bottomLeft = vec2(cornerRadius, 1.0 - cornerRadius);
	vec2 bottomRight = vec2(1.0 - cornerRadius, 1.0 - cornerRadius);


	vec4 texColour = texture(guiTexture,textureCoords);

	out_Color = vec4(texColour.x, texColour.y, texColour.z, opacity);
	
	out_Color = out_Color + vec4(modColour, out_Color.a);

	if(textureCoords.x < cornerRadius && textureCoords.y < cornerRadius){
		if(distance(topLeft, textureCoords) > cornerRadius){
			out_Color = vec4(0.0,0.0,0.0,0.0);
		}
	}else if(textureCoords.x > 1.0 - cornerRadius && textureCoords.y < cornerRadius){
		if(distance(topRight, textureCoords) > cornerRadius){
			out_Color = vec4(0.0,0.0,0.0,0.0);
		}
	}else if(textureCoords.y > 1.0 - cornerRadius && textureCoords.x < cornerRadius){
		if(distance(bottomLeft, textureCoords) > cornerRadius){
			out_Color = vec4(0.0,0.0,0.0,0.0);
		}
	}else if(textureCoords.x > 1.0 - cornerRadius && textureCoords.y > 1.0 - cornerRadius){
		if(distance(bottomRight, textureCoords) > cornerRadius){
			out_Color = vec4(0.0,0.0,0.0,0.0);
		}
	}
}