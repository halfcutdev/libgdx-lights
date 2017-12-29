#ifdef GL_ES
#define LOWP lowp
    precision mediump float;
#else
    #define LOWP
#endif

varying LOWP vec4 v_color;
varying vec2 v_texCoord;

uniform sampler2D u_texture;
uniform sampler2D u_lightmap;

uniform vec4 u_ambientColor;
uniform vec2 u_resolution;

void main() {

    vec4 diffuseColor = texture2D(u_texture, v_texCoord);

    vec2 lightCoord = (gl_FragCoord.xy / u_resolution.xy);
    vec4 light = texture2D(u_lightmap, lightCoord);

    vec3 ambient = u_ambientColor.rgb * u_ambientColor.a;
    vec3 intensity = ambient + light.rgb;
    vec3 final = diffuseColor.rgb * intensity;
    gl_FragColor = v_color * vec4(final, diffuseColor.a);


//    vec2 lightCoord = (gl_FragCoord.xy / u_resolution.xy);
//    vec4 light = texture2D(u_lightmap, lightCoord);
//    gl_FragColor = v_color * light;
}

/* Diffuse colour */

//void main() {
//    vec4 diffuseColor = texture2D(u_texture, v_texCoord);
//    vec3 ambient = u_ambientColor.rgb * u_ambientColor.a;
//    vec3 final = v_color.rgb * diffuseColor.rgb * ambient;
//    gl_FragColor = vec4(final, diffuseColor.a);
//}

/* Default */

//void main() {
//    gl_FragColor = v_color * texture2D(u_texture, v_texCoord);
//}