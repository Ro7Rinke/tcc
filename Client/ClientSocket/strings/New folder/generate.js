//32-126 chars
fs = require('fs')

const randomChar = () => {
    return 32 + Math.random() * (127 - 32)
}

let str = ""
while(str.length < 512){
    str = str + String.fromCharCode(randomChar())
}
fs.writeFile('rsa.dat', str, 'utf8', (error) => {
    if(error) console.log(error)
})

str = ""
while(str.length < 50*1024){
    str = str + String.fromCharCode(randomChar())
}
fs.writeFile('50.dat', str, 'utf8', (error) => {
    if(error) console.log(error)
})

str = ""
while(str.length < 500*1024){
    str = str + String.fromCharCode(randomChar())
}
fs.writeFile('500.dat', str, 'utf8', (error) => {
    if(error) console.log(error)
})

str = ""
while(str.length < 5120*1024){
    str = str + String.fromCharCode(randomChar())
}
fs.writeFile('5120.dat', str, 'utf8', (error) => {
    if(error) console.log(error)
})