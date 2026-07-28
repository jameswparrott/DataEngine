#version 330

in vec2 outTexCoord;

uniform sampler2D texSampler;

out vec4 fragColor;

void main()
{
    fragColor = texture(texSampler, outTexCoord);
}