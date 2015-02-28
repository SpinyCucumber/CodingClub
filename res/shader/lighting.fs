uniform float time;
uniform sampler2D textureUnit;
uniform vec2 resolution;
uniform vec4 color;

varying vec4 vertex;
varying vec2 texCoord;

void main() {
    vec2 pos = (gl_ModelViewMatrix * vertex).xy;
    float f = sin(6 * gl_FragCoord.x * gl_FragCoord.y);
    vec4 color = texture2D(textureUnit, texCoord);
    gl_FragColor = vec4(vec3(f * color.rgb), color.a);
}