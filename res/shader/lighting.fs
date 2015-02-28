uniform float time;
uniform sampler2D textureUnit;
uniform vec2 resolution;
uniform vec4 color;

varying vec4 vertex;
varying vec2 texCoord;

void main() {
    vec4 color = texture2D(textureUnit, texCoord);
    gl_FragColor = color;
}