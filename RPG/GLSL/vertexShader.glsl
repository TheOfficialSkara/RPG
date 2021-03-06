#version 330 core

in vec4 in_Position;
in vec4 in_Color;
in vec2 in_TextureCoord;
in vec3 in_Normal;

out vec4 pass_Color;
out vec2 pass_TextureCoord;
out vec3 pass_Normal;

void main(void) {
	gl_Position = in_Position;	
	pass_Color = in_Color;
	pass_TextureCoord = in_TextureCoord;	
	pass_Normal = in_Normal;
}