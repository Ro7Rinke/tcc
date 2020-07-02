const {execSync} = require('child_process')
const fs = require('fs')


let params = [
    // {
    //     algorithm: 'rsa',//nome do algoritmo
    //     key: 1024,//tamanho da chave
    //     data: 'rsa',//nome do arquivo q contem a string a ser criptografada
    //     run: 1,//quantidade de vezes q sera executado a criptografia e o resultado sera a media
    // },
    // {
    //     algorithm: 'rsa',//nome do algoritmo
    //     key: 2048,//tamanho da chave
    //     data: 'rsa',//nome do arquivo q contem a string a ser criptografada
    //     run: 3,//quantidade de vezes q sera executado a criptografia e o resultado sera a media
    // },
    // {
    //     algorithm: 'aes',//nome do algoritmo
    //     key: 128,//tamanho da chave
    //     data: '50',//nome do arquivo q contem a string a ser criptografada
    //     run: 3,//quantidade de vezes q sera executado a criptografia e o resultado sera a media
    // },
    {
        algorithm: 'aes',//nome do algoritmo
        key: 128,//tamanho da chave
        data: 'rsa',//nome do arquivo q contem a string a ser criptografada
        run: 1,//quantidade de vezes q sera executado a criptografia e o resultado sera a media
    },
    // {
    //     algorithm: 'aes',//nome do algoritmo
    //     key: 128,//tamanho da chave
    //     data: '5120',//nome do arquivo q contem a string a ser criptografada
    //     run: 3,//quantidade de vezes q sera executado a criptografia e o resultado sera a media
    // },
    // {
    //     algorithm: 'blowfish',//nome do algoritmo
    //     key: 448,//tamanho da chave
    //     data: '500',//nome do arquivo q contem a string a ser criptografada
    //     run: 3,//quantidade de vezes q sera executado a criptografia e o resultado sera a media
    // },
    // {
    //     algorithm: 'blowfish',//nome do algoritmo
    //     key: 128,//tamanho da chave
    //     data: '500',//nome do arquivo q contem a string a ser criptografada
    //     run: 3,//quantidade de vezes q sera executado a criptografia e o resultado sera a media
    // },
    
]

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

const main = () => {

    // execSync('del "./data/" /f /q /s')
    
    for(let paramIndex in params){
        for(let i = 0; i < params[paramIndex].run; i++){
            execSync(`cd ./data && java -jar ../dist/ClientSocket.jar ${params[paramIndex].data} ${params[paramIndex].algorithm} ${params[paramIndex].key} ${i}`)
            ReadResults(i, params[paramIndex])
        }
        
        for(let i in results)
            results[i] = Math.round(results[i])
    
        fs.writeFileSync(`./data/${params[paramIndex].data}-${params[paramIndex].algorithm}-${params[paramIndex].key}-results.dat`, JSON.stringify(results))
    }
}

main()

