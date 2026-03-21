/*
 * Copyright (c) 2025-2026 Terra Technology (Guangzhou) Co., Ltd.
 * Copyright (c) 2025-2026 泰若科技（广州）有限公司.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package com.terra.ems.ems.controller;

import com.terra.ems.common.annotation.Log;
import com.terra.ems.common.domain.Result;
import com.terra.ems.common.enums.BusinessType;
import com.terra.ems.ems.service.siteimport.SiteImportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 场站配置导入控制器
 *
 * 接收 YAML 格式的场站配置文件，自动创建全部实体和关联关系。
 *
 * @author dengxueping
 * @since 2026-03-21
 */
@Slf4j
@Tag(name = "场站配置导入")
@RestController
@RequestMapping("/site-import")
@RequiredArgsConstructor
public class SiteImportController {

    private final SiteImportService siteImportService;

    /**
     * 导入场站配置文件
     *
     * 上传 YAML 格式的场站配置文件，系统自动解析并创建：
     * 能源类型、用能单元树、网关、数据源、用能设备、计量器具、计量点及所有关联关系。
     *
     * 支持重复导入（幂等）：已存在的实体根据编码匹配并更新，不会重复创建。
     */
    @Operation(summary = "导入场站配置文件")
    @PostMapping("/upload")
    public Result<SiteImportService.ImportResult> importSiteConfig(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.failure("请选择文件");
        }

        String filename = file.getOriginalFilename();
        if (filename == null || (!filename.endsWith(".yaml") && !filename.endsWith(".yml"))) {
            return Result.failure("仅支持 YAML 格式文件（.yaml 或 .yml）");
        }

        try {
            log.info("开始导入场站配置: {}", filename);
            SiteImportService.ImportResult result = siteImportService.importSiteConfig(file.getInputStream());
            log.info("场站配置导入成功: {}", result);
            return Result.content(result);
        } catch (Exception e) {
            log.error("场站配置导入失败: {}", e.getMessage(), e);
            return Result.failure("导入失败: " + e.getMessage());
        }
    }
}
