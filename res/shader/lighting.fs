uniform float time;
uniform sampler2D textureUnit;
uniform vec2 resolution;
uniform float radius;
uniform vec3 fog;

varying vec4 vertex;
varying vec2 texCoord;
uniform vec2 point;

void main() {
	gl_FragColor = texture(textureUnit, texCoord) * (1.0 - distance(gl_FragCoord.xy, resolution / 2) / radius);
}