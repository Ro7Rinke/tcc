const {execSync} = require('child_process')
const fs = require('fs')

let config = {}

config.params = [
    // {
    //     algorithm: 'rsa',//nome do algoritmo
    //     key: 1024,//tamanho da chave em bits
    //     data: 50,//tamanho da string a ser criptografada em bytes
    //     run: 1,//quantidade de vezes q sera executado a criptografia e o resultado sera a media
    // },
    // {
    //     algorithm: 'rsa',//nome do algoritmo
    //     key: 2048,//tamanho da chave em bits
    //     data: 50,//tamanho da string a ser criptografada em bytes
    //     run: 3,//quantidade de vezes q sera executado a criptografia e o resultado sera a media
    // },
    // {
    //     algorithm: 'aes',//nome do algoritmo
    //     key: 128,//tamanho da chave em bits
    //     data: 50,//tamanho da string a ser criptografada em bytes
    //     run: 3,//quantidade de vezes q sera executado a criptografia e o resultado sera a media
    // },
    // {
    //     algorithm: 'aes',//nome do algoritmo
    //     key: 128,//tamanho da chave em bits
    //     data: 50,//tamanho da string a ser criptografada em bytes
    //     run: 1,//quantidade de vezes q sera executado a criptografia e o resultado sera a media
    // },
    // {
    //     algorithm: 'aes',//nome do algoritmo
    //     key: 128,//tamanho da chave em bits
    //     data: 5120,//tamanho da string a ser criptografada em bytes
    //     run: 3,//quantidade de vezes q sera executado a criptografia e o resultado sera a media
    // },
    // {
    //     algorithm: 'blowfish',//nome do algoritmo
    //     key: 448,//tamanho da chave em bits
    //     data: 500,//tamanho da string a ser criptografada em bytes
    //     run: 3,//quantidade de vezes q sera executado a criptografia e o resultado sera a media
    // },
    // {
    //     algorithm: 'blowfish',//nome do algoritmo
    //     key: 128,//tamanho da chave em bits
    //     data: 500,//tamanho da string a ser criptografada em bytes
    //     run: 3,//quantidade de vezes q sera executado a criptografia e o resultado sera a media
    // },
    // {
    //     algorithm: 'des',//nome do algoritmo
    //     key: 56,//tamanho da chave em bits
    //     data: 50,//tamanho da string a ser criptografada em bytes
    //     run: 3,//quantidade de vezes q sera executado a criptografia e o resultado sera a media
    // },
    
]

const algorithms = {
    des: {
        verifyParam: (param) => {
            
            if(param.key != 56)
                return false
            
            return true
        },
    },

    aes: {
        verifyParam: (param)  => {
            if(param.key != 128 && param.key != 192 && param.key != 256)
                return false
    
            return true
        }
    },

    blowfish: {
        verifyParam: (param)  => {
            if(param.key < 32 || param.key > 448)
                return false
    
            return true
        }
    },

    rsa: {
        verifyParam: (param) => {
            if(param.key < 512 || param.key > 16384)
                return false
    
            const maxDataSize = (param.key / 8) - 11//bytes
            
            if(param.data > maxDataSize)
                return false
    
            return true
        } 
    }
}

let results = {
    cryptTime: null,
    decryptTime: null,
    cryptSize: null,
    cryptMemory: null,
    decryptMemory: null,
    keyTime: null
}

const ReadResults = (i, prm) => {
    let data
    
    data = fs.readFileSync(`./data/singleresults/${prm.data}-${prm.algorithm}-${prm.key}-sizeCrypt-${i}.dat`)
    if(results.cryptSize == null)
        results.cryptSize = (parseInt(data) + results.cryptSize) / 2
    else
        results.cryptSize = (parseInt(data) + results.cryptSize) / 2

    data = fs.readFileSync(`./data/singleresults/${prm.data}-${prm.algorithm}-${prm.key}-usedTimeCrypt-${i}.dat`)
    if(results.cryptTime == null)
        results.cryptTime = parseInt(data)
    else
        results.cryptTime = (parseInt(data) + results.cryptTime) / 2

    data = fs.readFileSync(`./data/singleresults/${prm.data}-${prm.algorithm}-${prm.key}-usedTimeDecrypt-${i}.dat`)
    if(results.decryptTime == null)
        results.decryptTime = parseInt(data)
    else
        results.decryptTime = (parseInt(data) + results.decryptTime) / 2

    data = fs.readFileSync(`./data/singleresults/${prm.data}-${prm.algorithm}-${prm.key}-useMemoryCrypt-${i}.dat`)
    if(results.cryptMemory == null)
        results.cryptMemory = parseInt(data)
    else
        results.cryptMemory = (parseInt(data) + results.cryptMemory) / 2

    data = fs.readFileSync(`./data/singleresults/${prm.data}-${prm.algorithm}-${prm.key}-useMemoryDecrypt-${i}.dat`)
    if(results.decryptMemory == null)
        results.decryptMemory = parseInt(data)
    else
        results.decryptMemory = (parseInt(data) + results.decryptMemory) / 2

    data = fs.readFileSync(`./data/singleresults/${prm.data}-${prm.algorithm}-${prm.key}-usedTimeKey-${i}.dat`)
    if(results.keyTime == null)
        results.keyTime = parseInt(data)
    else
        results.keyTime = (parseInt(data) + results.keyTime) / 2
}

const loadConfig = () => {
    config = JSON.parse(fs.readFileSync('./config.json'))
}

const verifyParams = () => {
    let validParams = []

    for(let i in config.params){
        if(algorithms[config.parms[i].algorithm])
            if(algorithms[config.parms[i].algorithm].verifyParam())
                validParams.push(config.parms[i])
    }

    config.params = validParams
}

const main = () => {

    loadConfig()

    verifyParams()

    
    for(let paramIndex in config.params){
        for(let i = 0; i < config.params[paramIndex].run; i++){
            execSync(`cd ./data && java -jar ../dist/ClientSocket.jar ${config.params[paramIndex].data} ${config.params[paramIndex].algorithm} ${config.params[paramIndex].key} ${i}`)
            ReadResults(i, config.params[paramIndex])
        }
        
        for(let i in results)
            results[i] = Math.round(results[i])
    
        fs.writeFileSync(`./data/${config.params[paramIndex].data}-${config.params[paramIndex].algorithm}-${config.params[paramIndex].key}-results.dat`, JSON.stringify(results))
    }

    execSync('del "./data/" /f /q /s')

    //generate graphic

}

main()

