protoc  --java_out=../  DeviceMessage.proto
protoc  --java_out=../  Certificate.proto
protoc  --js_out=import_style=commonjs,binary:../ Certificate.proto